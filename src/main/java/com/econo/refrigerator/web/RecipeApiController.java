package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import com.econo.refrigerator.service.RecipeService;
import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecipeApiController {

    private final RecipeService recipeService;

    @PostMapping("/api/recipe")
    public Long create(@RequestBody RecipeSaveDto recipeSaveDto) {
        return recipeService.create(recipeSaveDto);
    }

    @PutMapping("/api/recipe/{id}")
    public Long update(@PathVariable Long id, @RequestBody RecipeSaveDto recipeSaveDto) {
        return recipeService.update(id, recipeSaveDto);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe read(@PathVariable Long id) {
        return recipeService.read(id);
    }

    @DeleteMapping("/api/recipe/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }

    @PostMapping("/api/recipe/ingredient/{ingredient}")
    public Long createIngredient(@PathVariable String ingredient) {
        return recipeService.createIngredient(ingredient);
    }

    @DeleteMapping("/api/recipe/ingreident/{ingredient}")
    public void deleteIngredient(@PathVariable String ingredient) {
        recipeService.deleteIngredient(ingredient);
    }

    @GetMapping("/api/recipe/ingredient")
    public List<RecipeIngredient> getIngredientList() {
        return recipeService.getIngredientList();
    }

    @PutMapping("/api/recipe/{id}/ingredient/{ingredient}")
    public void appendIngredient(@PathVariable Long id, @PathVariable String ingredient) {
        recipeService.appendIngredient(id, ingredient);
    }

    @DeleteMapping("/api/recipe/{id}/ingredient/{ingredient}")
    public void subtractIngredient(@PathVariable Long id, @PathVariable String ingredient) {
        recipeService.subtractIngredient(id, ingredient);
    }
}
