package ru.mkilord.tacos.rep;

import org.springframework.stereotype.Repository;
import ru.mkilord.tacos.entites.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
