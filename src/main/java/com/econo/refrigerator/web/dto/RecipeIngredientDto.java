package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Ingredient;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecipeIngredientDto {

    private Ingredient ingredient;

    public RecipeIngredientDto(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeIngredient toEntity() {
        return RecipeIngredient.builder()
                .ingredient(ingredient)
                .build();
    }
}
