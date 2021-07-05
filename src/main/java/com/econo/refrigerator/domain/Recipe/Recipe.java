package com.econo.refrigerator.domain.Recipe;

import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "recipe_ingredient_relation",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_ingredient_id")
    )
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    // cooking method description
    private String cookingDescription;

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

    public void appendIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public void subtractIngredien(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);
    }
}
