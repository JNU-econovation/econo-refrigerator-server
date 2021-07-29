package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Ingredient;
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
    public RecipeIngredientsDto(List<Integer> ingredients) {
        recipeIngredientDtos = new ArrayList<>();

        for (Integer ingredient : ingredients) {
            recipeIngredientDtos.add(new RecipeIngredientDto(Ingredient.values()[ingredient]));

        }
    }
}
