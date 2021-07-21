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

    public String name;
    public String description;
    public String imagePath;
    public List<RecipeIngredientDto> ingredients;
    public List<StepDto> steps;

    @Builder
    public RecipeDto(String name, String description, String imagePath,
                     List<RecipeIngredientDto> ingredients, List<StepDto> steps) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .name(name)
                .description(description)
                .imagePath(imagePath)
                .build();
    }
}
