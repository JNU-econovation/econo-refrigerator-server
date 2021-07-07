package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeIngredientDto;
import com.econo.refrigerator.web.dto.StepDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<RecipeIngredient> getIngredientList() {
        return recipeIngreidentRepository.findAll();
    }

    public void deleteIngredient(Ingredient ingredient) {
        recipeIngreidentRepository.delete(recipeIngreidentRepository.findByIngredient(ingredient));
    }

    @Transactional
    public void appendIngredient(Long recipeId, Ingredient ingredient) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(ingredient);
        System.out.println("@@@@@@@" + recipeIngredient.getIngredient().name());

        recipe.appendIngredient(recipeIngredient);
    }

    @Transactional
    public void appendIngredient(Recipe recipe, Ingredient ingredient) {
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(ingredient);
        recipeIngredient.appendRecipe(recipe);
        //recipe.appendIngredient(recipeIngredient);
    }

    @Transactional
    public void subtractIngredient(Long recipeId, Ingredient ingredient) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(ingredient);

        recipe.subtractIngredien(recipeIngredient);
    }

    @Transactional
    public void subtractIngredient(Recipe recipe, Ingredient ingredient) {
        RecipeIngredient recipeIngredient = recipeIngreidentRepository.findByIngredient(ingredient);
        recipe.subtractIngredien(recipeIngredient);
    }

    @Transactional
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

    @Transactional
    // delete & subtract (different with ingredient)
    public void deleteStep(Long stepId) {
        Step step = stepRepository.findById(stepId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요리법이 없습니다. id = " + stepId));

        stepRepository.delete(step);
    }
}
