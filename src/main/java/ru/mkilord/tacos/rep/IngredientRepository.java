package ru.mkilord.tacos.rep;

import org.springframework.data.repository.Repository;
import ru.mkilord.tacos.entites.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends Repository<Ingredient,String> {
    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
