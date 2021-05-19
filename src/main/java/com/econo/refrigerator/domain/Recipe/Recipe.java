package com.econo.refrigerator.domain.Recipe;

import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // ingredients array

    // cook method description array

    // cook method picture array

    @Builder
    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void update(RecipeSaveDto recipeSaveDto) {
        if (recipeSaveDto.getName() != null)
            this.name = recipeSaveDto.getName();
        if (recipeSaveDto.getDescription() != null)
            this.description = recipeSaveDto.getDescription();
    }
}
