package ru.mkilord.tacos.data;

import org.springframework.data.repository.CrudRepository;
import ru.mkilord.tacos.entites.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
