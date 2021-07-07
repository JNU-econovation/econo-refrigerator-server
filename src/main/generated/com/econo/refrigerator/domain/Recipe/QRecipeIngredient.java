package com.econo.refrigerator.domain.Recipe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeIngredient is a Querydsl query type for RecipeIngredient
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipeIngredient extends EntityPathBase<RecipeIngredient> {

    private static final long serialVersionUID = -1943863430L;

    public static final QRecipeIngredient recipeIngredient = new QRecipeIngredient("recipeIngredient");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Ingredient> ingredient = createEnum("ingredient", Ingredient.class);

    public final ListPath<Recipe, QRecipe> recipes = this.<Recipe, QRecipe>createList("recipes", Recipe.class, QRecipe.class, PathInits.DIRECT2);

    public QRecipeIngredient(String variable) {
        super(RecipeIngredient.class, forVariable(variable));
    }

    public QRecipeIngredient(Path<? extends RecipeIngredient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipeIngredient(PathMetadata metadata) {
        super(RecipeIngredient.class, metadata);
    }

}

