package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.domain.Recipe.RecipeIngredient;
import com.econo.refrigerator.domain.Recipe.Step;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class RecipeDto {

    private String name;
    private String description;
    private List<RecipeIngredientDto> ingredients;
    private List<StepDto> steps;

    @Builder
    public RecipeDto(String name, String description, List<RecipeIngredientDto> ingredients, List<StepDto> steps) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .name(name)
                .description(description)
                .build();
    }
}
