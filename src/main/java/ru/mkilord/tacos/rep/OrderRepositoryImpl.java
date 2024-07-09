package ru.mkilord.tacos.rep;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.mkilord.tacos.entites.Ingredient;
import ru.mkilord.tacos.entites.IngredientRef;
import ru.mkilord.tacos.entites.Taco;
import ru.mkilord.tacos.entites.TacoOrder;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcOperations jdbcOperation;

    public OrderRepositoryImpl(JdbcOperations jdbcOperation) {
        this.jdbcOperation = jdbcOperation;
    }

    @Transactional
    @Override
    public TacoOrder save(TacoOrder order) {
        var pscf = getPSCF();

        order.setPlacedAt(new Date());

        var psc = pscf.newPreparedStatementCreator(new Object[]{
                order.getDeliveryName(),
                order.getDeliveryStreet(),
                order.getDeliveryCity(),
                order.getDeliveryState(),
                order.getDeliveryZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV(),
                order.getPlacedAt()
        });

        var keyHolder = new GeneratedKeyHolder();
        /* Здесь мы даём KeyHolder в метод update,
        чтобы достать значение ключа, которое выдаст база */
        jdbcOperation.update(psc, keyHolder);
        var orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        var i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    private long saveTaco(long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        var pscf = new PreparedStatementCreatorFactory(
                "insert into Taco " +
                        "(id, name, taco_order, taco_order_key, created_at) " +
                        "VALUES (?,?,?,?,?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
        pscf.setReturnGeneratedKeys(true);
        var psc = pscf.newPreparedStatementCreator(
                new Object[]{
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey
                }
        );

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperation.update(psc, keyHolder);
        var tacoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientsRefs) {
        var key = 0;
        for (IngredientRef ingredientsRef : ingredientsRefs) {
           jdbcOperation.update("insert into Ingredient_Ref (ingredient, taco, taco_key)" +
                   " VALUES (?,?,?)",
                   ingredientsRef.getIngredient(),tacoId,key++);
        }
    }

    private static PreparedStatementCreatorFactory getPSCF() {
        var pscf = new PreparedStatementCreatorFactory(
                "insert into Taco_Order " +
                        "(id, delivery_name, delivery_street, delivery_city," +
                        " delivery_state, delivery_zip, cc_number, cc_expiration," +
                        " cc_cvv, placed_at)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);
        return pscf;
    }
}
