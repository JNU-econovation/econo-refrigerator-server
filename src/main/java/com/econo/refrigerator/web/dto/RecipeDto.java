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
    private String cookingDescription;

    @Builder
    public RecipeDto(String name, String description, String cookingDescription) {
        this.name = name;
        this.description = description;
        this.cookingDescription = cookingDescription;
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .name(name)
                .description(description)
                .cookingDescription(cookingDescription)
                .build();
    }
}
