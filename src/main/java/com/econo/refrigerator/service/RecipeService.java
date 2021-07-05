package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngreidentRepository recipeIngreidentRepository;

    public Long create(RecipeSaveDto recipeSaveDto) {
        return recipeRepository.save(recipeSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, RecipeSaveDto recipeSaveDto) {
        Recipe targetRecipe = read(id);
        targetRecipe.update(recipeSaveDto);

        return id;
    }

    public Recipe read(Long id) throws IllegalArgumentException {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
    }

    public void delete(Long id) throws IllegalArgumentException {
        Recipe targetRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
        recipeRepository.delete(targetRecipe);
    }

    public Long createIngredient(String ingredient) {
        return recipeIngreidentRepository.save(
                RecipeIngredient.builder()
                .ingredient(Ingredient.valueOf(ingredient))
                .build()
        ).getId();
    }

    public void deleteIngredient(String ingredient) {
        recipeIngreidentRepository.delete(
                recipeIngreidentRepository.findByIngredient(Ingredient.valueOf(ingredient))
        );
    }

    public List<RecipeIngredient> getIngredientList() {
        return recipeIngreidentRepository.findAll();
    }

    public void appendIngredient(Long id, String ingredient) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(Ingredient.valueOf(ingredient));

        recipe.appendIngredient(recipeIngredient);
    }

    public void subtractIngredient(Long id, String ingredient) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(Ingredient.valueOf(ingredient));

        recipe.subtractIngredien(recipeIngredient);
    }
}
