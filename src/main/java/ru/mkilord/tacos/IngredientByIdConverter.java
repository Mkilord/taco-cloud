package ru.mkilord.tacos;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mkilord.tacos.entites.Ingredient;
import ru.mkilord.tacos.rep.IngredientRepository;

import java.util.HashMap;
import java.util.Map;

import static ru.mkilord.tacos.entites.Ingredient.*;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
