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

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps = new ArrayList<>();

    private Integer likeCount = 0;

    private Float averageGrade = 0.0f;

    @OneToMany(mappedBy = "recipe")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void update(RecipeDto recipeDto) {
        this.name = recipeDto.getName();
        this.description = recipeDto.getDescription();
        this.ingredients = recipeDto.getRecipeIngredientEntities();
        this.steps = recipeDto.getStepEntities();
    }

    public void appendIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public void subtractIngredien(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void rateLike() {
        likeCount++;
    }

    public void rateUnLike() {
        likeCount--;
    }
}
