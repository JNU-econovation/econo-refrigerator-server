package com.econo.refrigerator.web;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.service.RecipeService;
import com.econo.refrigerator.web.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
}
