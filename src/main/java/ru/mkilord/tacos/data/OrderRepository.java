package ru.mkilord.tacos.data;

import org.springframework.data.repository.CrudRepository;
import ru.mkilord.tacos.entites.TacoOrder;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);
    List<TacoOrder> readTacoOrdersByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date startDate, Date endDate);
}
