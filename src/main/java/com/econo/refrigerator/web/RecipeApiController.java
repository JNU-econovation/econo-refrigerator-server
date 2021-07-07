package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import com.econo.refrigerator.service.RecipeService;
import com.econo.refrigerator.web.dto.RecipeDto;
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

    @PutMapping("/api/recipe/{id}")
    public Long update(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        return recipeService.update(id, recipeDto);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe read(@PathVariable Long id) {
        return recipeService.read(id);
    }

    @DeleteMapping("/api/recipe/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }

    @GetMapping("/api/recipe/ingredient")
    public List<RecipeIngredient> getIngredientList() {
        return recipeService.getIngredientList();
    }

    @PutMapping("/api/recipe/{recipeId}/ingredient/{ingredientId}")
    public void appendIngredient(@PathVariable Long recipeId, @PathVariable Integer ingredientId) {
        recipeService.appendIngredient(recipeId, ingredientId);
    }

    @DeleteMapping("/api/recipe/{recipeId}/ingredient/{ingredientId}")
    public void subtractIngredient(@PathVariable Long recipeId, @PathVariable Integer ingredientId) {
        recipeService.subtractIngredient(recipeId, ingredientId);
    }

    @PutMapping("/api/recipe/{recipeId}/like")
    public void rateLike(@PathVariable Long recipeId) {
        recipeService.rateLike(recipeId);
    }

    @PutMapping("/api/recipe/{recipeId}/unlike")
    public void rateUnLike(@PathVariable Long recipeId) {
        recipeService.rateUnLike(recipeId);
    }
}
