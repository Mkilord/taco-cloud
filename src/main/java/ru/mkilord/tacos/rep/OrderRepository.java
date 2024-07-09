package ru.mkilord.tacos.rep;

import ru.mkilord.tacos.entites.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
