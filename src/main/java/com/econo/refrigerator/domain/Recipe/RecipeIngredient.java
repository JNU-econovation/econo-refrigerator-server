package com.econo.refrigerator.domain.Recipe;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Ingredient ingredient;

    @ManyToMany(mappedBy = "ingredients")
    @JsonBackReference
    private List<Recipe> recipes = new ArrayList<>();

    @Builder
    public RecipeIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public void appendRecipe(Recipe recipe) { this.recipes.add(recipe); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return id.equals(that.id) && ingredient == that.ingredient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient);
    }
}
