package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngreidentRepository recipeIngreidentRepository;

    public Long create(RecipeDto recipeDto) {


        return recipeRepository.save(recipeDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, RecipeDto recipeDto) {
        Recipe targetRecipe = read(id);
        targetRecipe.update(recipeDto);

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

    public List<RecipeIngredient> getIngredientList() {
        return recipeIngreidentRepository.findAll();
    }

    public void appendIngredient(Long recipeId, Integer ingredientId) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(Ingredient.values()[ingredientId]);

        recipe.appendIngredient(recipeIngredient);
    }

    public void subtractIngredient(Long recipeId, Integer ingredientId) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(Ingredient.values()[ingredientId]);

        recipe.subtractIngredien(recipeIngredient);
    }

    @Transactional
    public void rateLike(Long recipeId) {
        Recipe recipe = read(recipeId);

        recipe.rateLike();
    }

    @Transactional
    public void rateUnLike(Long recipeId) {
        Recipe recipe = read(recipeId);

        recipe.rateUnLike();
    }
}
