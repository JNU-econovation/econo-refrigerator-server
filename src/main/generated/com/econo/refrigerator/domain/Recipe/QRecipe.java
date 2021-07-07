package com.econo.refrigerator.domain.Recipe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = 1872517321L;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final NumberPath<Float> averageGrade = createNumber("averageGrade", Float.class);

    public final ListPath<com.econo.refrigerator.domain.Comment.Comment, com.econo.refrigerator.domain.Comment.QComment> comments = this.<com.econo.refrigerator.domain.Comment.Comment, com.econo.refrigerator.domain.Comment.QComment>createList("comments", com.econo.refrigerator.domain.Comment.Comment.class, com.econo.refrigerator.domain.Comment.QComment.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RecipeIngredient, QRecipeIngredient> ingredients = this.<RecipeIngredient, QRecipeIngredient>createList("ingredients", RecipeIngredient.class, QRecipeIngredient.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<Step, QStep> steps = this.<Step, QStep>createList("steps", Step.class, QStep.class, PathInits.DIRECT2);

    public QRecipe(String variable) {
        super(Recipe.class, forVariable(variable));
    }

    public QRecipe(Path<? extends Recipe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipe(PathMetadata metadata) {
        super(Recipe.class, metadata);
    }

}

