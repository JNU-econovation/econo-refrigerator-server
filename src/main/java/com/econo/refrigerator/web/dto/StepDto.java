package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Recipe.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StepDto {

    private String description;
    private String imagePath;

    public StepDto(String description, String imagePath) {
        this.description = description;
        this.imagePath = imagePath;
    }

    public Step toEntity() {
        return Step.builder()
                .description(description)
                .imagePath(imagePath)
                .build();
    }
}
