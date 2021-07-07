package com.econo.refrigerator.domain.Recipe;

import com.econo.refrigerator.web.dto.StepDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Recipe recipe;

    @Builder
    public Step(String description, String imagePath, Recipe recipe) {
        this.description = description;
        this.imagePath = imagePath;
        this.recipe = recipe;
    }

    public boolean equalsWithDto(StepDto dto) {
        if (description.equals(dto.getDescription()) && imagePath.equals(dto.getImagePath()))
            return true;
        else
            return false;
    }

    public void update(StepDto dto) {
        this.description = dto.getDescription();
        this.imagePath = dto.getImagePath();
    }
}
