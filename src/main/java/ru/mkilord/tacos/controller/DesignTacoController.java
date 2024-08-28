package ru.mkilord.tacos.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.mkilord.tacos.data.IngredientRepository;
import ru.mkilord.tacos.entites.Ingredient;
import ru.mkilord.tacos.entites.Taco;
import ru.mkilord.tacos.entites.TacoOrder;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.mkilord.tacos.entites.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@AllArgsConstructor
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        var ingredients = ingredientRepository.findAll();
        var types = Type.values();
        for (var type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
            System.out.println(type.toString().toLowerCase());
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) return "design";
        tacoOrder.addTaco(taco);
        log.info("Processing taco: \n{}", taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
            Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
