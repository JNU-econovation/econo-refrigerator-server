package com.econo.refrigerator.domain.Recipe;

import com.econo.refrigerator.domain.Comment.Comment;
import com.econo.refrigerator.web.dto.RecipeDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String cookingMethod;

    // cook method picture array

    private Integer gradeCount = 0;

    private Float averageGrade = 0.0f;

    @OneToMany(mappedBy = "recipe")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Recipe(String name, String description, String cookingMethod) {
        this.name = name;
        this.description = description;
        this.cookingMethod = cookingMethod;
    }

    public void update(RecipeDto recipeDto) {
        this.name = recipeDto.getName();
        this.description = recipeDto.getDescription();
        this.cookingMethod = recipeDto.getCookingMethod();
    }

    public void appendIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public void subtractIngredien(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void calculateAverageGrade(Integer newGrade) {
        Float sumOfGrade = averageGrade * gradeCount;

        gradeCount++;
        averageGrade = (sumOfGrade + newGrade) / gradeCount;
    }
}
