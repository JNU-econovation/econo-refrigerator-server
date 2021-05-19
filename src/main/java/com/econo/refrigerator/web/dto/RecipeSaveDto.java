package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeSaveDto {

    private String name;
    private String description;

    @Builder
    public RecipeSaveDto (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .name(name)
                .description(description)
                .build();
    }
}
