package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.service.RecipeService;
import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecipeApiController {

    private final RecipeService recipeService;

    @PostMapping("/api/recipe/create")
    public Long create(@RequestBody RecipeSaveDto recipeSaveDto) {
        return recipeService.create(recipeSaveDto);
    }

    @PutMapping("/api/recipe/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody RecipeSaveDto recipeSaveDto) {
        return recipeService.update(id, recipeSaveDto);
    }

    @GetMapping("/api/recipe/read/{id}")
    public Recipe read(@PathVariable Long id) {
        return recipeService.read(id);
    }

    @DeleteMapping("/api/recipe/delete/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }
}
