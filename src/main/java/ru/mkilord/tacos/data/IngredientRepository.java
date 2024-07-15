package ru.mkilord.tacos.data;

import org.springframework.data.repository.CrudRepository;
import ru.mkilord.tacos.entites.Ingredient;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
