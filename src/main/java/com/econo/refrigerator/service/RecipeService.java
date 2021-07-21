package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.*;
import com.econo.refrigerator.web.dto.RecipeDto;
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
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeQueryRepository recipeQueryRepository;
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
    public List<Recipe> find10RecipeByIngredients(RecipeIngredientsDto recipeIngredientsDto) {
        List<RecipeIngredient> recipeIngredients =
                recipePropertyService.convertRecipeIngredientsDtoIntoEntities(recipeIngredientsDto);

        return recipeQueryRepository.searchRecipesByIngredientsOnce(recipeIngredients);
    }

    @Transactional
    public Long update(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = read(recipeId);

        List<RecipeIngredient> newIngredients = new ArrayList<>();
        for (RecipeIngredientDto dto : recipeDto.getIngredients()) {
            RecipeIngredient entity = recipePropertyService.findByIngredient(dto.getIngredient());
            newIngredients.add(entity);
        }

        recipe.update(recipeDto, newIngredients);

        // Step Update
        // Modify as much as the same size
        List<Step> curSteps = recipe.getSteps();
        List<StepDto> newSteps = recipeDto.getSteps();
        int commonIdx = Math.min(curSteps.size(), newSteps.size());
        for (int i = 0; i < commonIdx; i++) {
            if (curSteps.get(i).equalsWithDto(newSteps.get(i))) continue;

            curSteps.get(i).update(newSteps.get(i));
        }

        // Delete or Create as much as different
        if (curSteps.size() > newSteps.size()) {
                int removedidx = newSteps.size();
                for (int i = removedidx; i < curSteps.size(); i++) {
//                    curSteps.remove(i);
                    recipePropertyService.deleteStep(curSteps.get(i));
                }
        } else {
            int createdIdx = curSteps.size();
            for (int i = createdIdx; i < newSteps.size(); i++) {
                recipePropertyService.createStep(recipe, newSteps.get(i));
            }
        }

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
