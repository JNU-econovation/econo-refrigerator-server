package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Ingredient;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import com.econo.refrigerator.service.RecipePropertyService;
import com.econo.refrigerator.web.dto.RecipeIngredientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecipePropertyApiController {

    private final RecipePropertyService recipePropertyService;

    @PostMapping("/api/Ingredient")
    public Long createIngredient(@RequestBody RecipeIngredientDto recipeIngredientDto) {
        return recipePropertyService.createIngredient(recipeIngredientDto);
    }

    @GetMapping("/api/ingredient")
    public List<RecipeIngredient> getIngredientList() {
        return recipePropertyService.getIngredientList();
    }

    @DeleteMapping("/api/ingredient/{ingredientId}")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        recipePropertyService.deleteIngredient(Ingredient.values()[ingredientId.intValue()]);
    }

    @PutMapping("/api/recipe/{recipeId}/ingredient/{ingredientId}")
    public void appendIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        recipePropertyService.appendIngredient(recipeId, Ingredient.values()[ingredientId.intValue()]);
    }

    @DeleteMapping("/api/recipe/{recipeId}/ingredient/{ingredientId}")
    public void subtractIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        recipePropertyService.subtractIngredient(recipeId, Ingredient.values()[ingredientId.intValue()]);
    }
}
