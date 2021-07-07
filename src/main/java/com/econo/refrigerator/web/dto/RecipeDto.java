package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeDto {

    private String name;
    private String description;
    private String cookingMethod;

    @Builder
    public RecipeDto(String name, String description, String cookingMethod) {
        this.name = name;
        this.description = description;
        this.cookingMethod = cookingMethod;
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .name(name)
                .description(description)
                .cookingMethod(cookingMethod)
                .build();
    }
}
