package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeIngredientDto;
import com.econo.refrigerator.web.dto.RecipeIngredientsDto;
import com.econo.refrigerator.web.dto.StepDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipePropertyService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngreidentRepository recipeIngreidentRepository;
    private final StepRepository stepRepository;

    public Long createIngredient(RecipeIngredientDto recipeIngredientDto) {
        return recipeIngreidentRepository.save(recipeIngredientDto.toEntity()).getId();
    }
    
    public RecipeIngredient findByIngredient(Ingredient ingredient) {
        return recipeIngreidentRepository.findByIngredient(ingredient);
    }

    public List<RecipeIngredient> getIngredientList() {
        return recipeIngreidentRepository.findAll();
    }

    public void deleteIngredient(Ingredient ingredient) {
        recipeIngreidentRepository.delete(recipeIngreidentRepository.findByIngredient(ingredient));
    }

    @Transactional
    public void appendIngredient(Long recipeId, Ingredient ingredient) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = findByIngredient(ingredient);

        recipe.appendIngredient(recipeIngredient);
    }

    @Transactional
    public void appendIngredient(Recipe recipe, Ingredient ingredient) {
        RecipeIngredient recipeIngredient = findByIngredient(ingredient);
        recipe.appendIngredient(recipeIngredient);
    }

    @Transactional
    public void subtractIngredient(Long recipeId, Ingredient ingredient) throws IllegalArgumentException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = findByIngredient(ingredient);

        recipe.subtractIngredient(recipeIngredient);
    }

    @Transactional
    public List<RecipeIngredient> convertRecipeIngredientsDtoIntoEntities(RecipeIngredientsDto recipeIngredientsDto) {
        List<RecipeIngredient> entities = new ArrayList<>();
        for (RecipeIngredientDto dto : recipeIngredientsDto.getRecipeIngredientDtos()) {
            entities.add(findByIngredient(dto.getIngredient()));
        }

        return entities;
    }

    // create & append (different with ingredient)
    public Long createStep(Recipe recipe, StepDto stepDto) {
        Step step = Step.builder()
                        .description(stepDto.getDescription())
                        .imagePath(stepDto.getImagePath())
                        .recipe(recipe)
                        .build();
        stepRepository.save(step);

        return step.getId();
    }

    // delete & subtract (different with ingredient)
    public void deleteStep(Step step) {
        stepRepository.delete(step);
    }
}
