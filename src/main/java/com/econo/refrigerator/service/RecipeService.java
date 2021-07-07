package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeDto;
import com.econo.refrigerator.web.dto.RecipeIngredientDto;
import com.econo.refrigerator.web.dto.StepDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipePropertyService recipePropertyService;

    @Transactional
    public Long create(RecipeDto recipeDto) {
        Recipe recipe =  recipeRepository.save(recipeDto.toEntity());

        List<RecipeIngredientDto> recipeIngredientDtos = recipeDto.getIngredients();
        for (RecipeIngredientDto recipeIngredientDto : recipeIngredientDtos) {
            recipePropertyService.appendIngredient(recipe, recipeIngredientDto.getIngredient());
        }

        List<StepDto> stepDtos = recipeDto.getSteps();
        for (StepDto stepDto : stepDtos) {
            recipePropertyService.createStep(recipe, stepDto);
        }

        return recipe.getId();
    }

    @Transactional
    public Long update(Long recipeId, RecipeDto recipeDto) {
        Recipe targetRecipe = read(recipeId);
        targetRecipe.update(recipeDto);

        return recipeId;
    }

    public Recipe read(Long recipeId) throws IllegalArgumentException {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
    }

    public void delete(Long recipeId) throws IllegalArgumentException {
        Recipe targetRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + recipeId));
        recipeRepository.delete(targetRecipe);
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
