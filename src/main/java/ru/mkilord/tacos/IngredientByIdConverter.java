package ru.mkilord.tacos;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mkilord.tacos.entites.Ingredient;
import ru.mkilord.tacos.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepo;

    public IngredientByIdConverter(@Qualifier("ingredientRepository") IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
