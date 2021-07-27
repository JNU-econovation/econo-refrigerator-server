package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.service.RecipeService;
import com.econo.refrigerator.web.dto.RecipeDto;
import com.econo.refrigerator.web.dto.RecipeIngredientsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class RecipeApiController {

    private final RecipeService recipeService;

    @PostMapping("/api/recipe")
    public Long create(@RequestBody RecipeDto recipeDto) {
        return recipeService.create(recipeDto);
    }

    @PutMapping("/api/recipe/{recipeId}")
    public Long update(@PathVariable Long recipeId, @RequestBody RecipeDto recipeDto) {
        return recipeService.update(recipeId, recipeDto);
    }

    @GetMapping("/api/recipe/{recipeId}")
    public Recipe read(@PathVariable Long recipeId) {
        return recipeService.read(recipeId);
    }

    @GetMapping("/api/recipe/list/{offset}")
    public List<Recipe> get10RecipeWithOffset(@PathVariable Integer offset) {
        return recipeService.get10RecipeWithOffset(offset);
    }

    @GetMapping("/api/recipe/randomList")
    public List<Recipe> get10RandomRecipe() {
        return recipeService.get10RandomRecipe();
    }

    @DeleteMapping("/api/recipe/{recipeId}")
    public void delete(@PathVariable Long recipeId) {
        recipeService.delete(recipeId);
    }

    @PutMapping("/api/recipe/{recipeId}/like")
    public void rateLike(@PathVariable Long recipeId) {
        recipeService.rateLike(recipeId);
    }

    @PutMapping("/api/recipe/{recipeId}/unlike")
    public void rateUnLike(@PathVariable Long recipeId) {
        recipeService.rateUnLike(recipeId);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> find10RecipeByIngredients(@RequestBody RecipeIngredientsDto recipeIngredientsDto) {
        return recipeService.find10RecipeByIngredients(recipeIngredientsDto);
    }
}
