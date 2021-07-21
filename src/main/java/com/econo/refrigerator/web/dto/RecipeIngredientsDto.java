package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class RecipeIngredientsDto {

    public List<RecipeIngredientDto> recipeIngredientDtos;

    @Builder
    public RecipeIngredientsDto(List<RecipeIngredientDto> recipeIngredientDtos) {
        this.recipeIngredientDtos = recipeIngredientDtos;
    }
}
