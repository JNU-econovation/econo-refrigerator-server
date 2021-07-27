package com.econo.refrigerator.domain.Recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class RecipeSearchQueryRepository extends QuerydslRepositorySupport {

    private static final int ONCE_SEARCH_COUNT = 10;
    private static final int MORE_BUY_INGREDIENT_MAX_COUNT = 5;

    private final JPAQueryFactory queryFactory;
    private BooleanBuilder recursiveBuilder;

    public RecipeSearchQueryRepository(JPAQueryFactory queryFactory) {
        super(Recipe.class);
        this.queryFactory = queryFactory;
        this.recursiveBuilder = new BooleanBuilder();
    }

    @Transactional
    public List<Recipe> searchSufficientRecipesByIngredientsOnce(List<RecipeIngredient> targetIngredients) {
        List<Recipe> searchResult = new ArrayList<>();

        for (int includeCount = targetIngredients.size(); includeCount > 0; includeCount--) {
            if (searchResult.size() == ONCE_SEARCH_COUNT) {
                break;
            }

            List<BooleanBuilder> searchCombinationBuilders = generateAllSufficientSearchCombinationBuilders(targetIngredients, includeCount);
            List<Recipe> combinationSearchResult = searchRecipeByBuilderUntillUnable(searchCombinationBuilders, ONCE_SEARCH_COUNT - searchResult.size());
            searchResult.addAll(combinationSearchResult);
        }

        return searchResult;
    }

    @Transactional
    public List<Recipe> searchInsufficientRecipesByIngredientsOnce(List<RecipeIngredient> targetIngredients) {
        List<Recipe> searchResult = new ArrayList<>();

        for (int moreBuyCount = 1; moreBuyCount <= MORE_BUY_INGREDIENT_MAX_COUNT; moreBuyCount++) {
            if (searchResult.size() == ONCE_SEARCH_COUNT) {
                break;
            }

            BooleanBuilder builder = generateInsufficientSearchCombinationBuilder(targetIngredients, moreBuyCount);
            List<Recipe> searchedInsufficientRecipe = searchRecipeByBuilder(builder, ONCE_SEARCH_COUNT - searchResult.size());
            searchResult.addAll(searchedInsufficientRecipe);
        }

        return searchResult;
    }

    private List<Recipe> searchRecipeByBuilder(BooleanBuilder builder, int searchLimit) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .where(builder)
                .limit(searchLimit)
                .fetch();
    }

    private List<Recipe> searchRecipeByBuilderUntillUnable(List<BooleanBuilder> builders, int maxSearchResultCount) {
        List<Recipe> searchResult = new ArrayList<>();

        for (BooleanBuilder builder : builders) {
            if (searchResult.size() == maxSearchResultCount) {
                shuffleSearchResult(searchResult);
                break;
            }

            searchResult.addAll(searchRecipeByBuilder(builder, maxSearchResultCount - searchResult.size()));
        }

        return searchResult;
    }

    private List<BooleanBuilder> generateAllSufficientSearchCombinationBuilders(List<RecipeIngredient> targetIngredients, int includeCount) {
        List<BooleanBuilder> searchCombinationBuilders = new ArrayList<>();

        for (int includeStartIdx = 0; includeStartIdx < targetIngredients.size() - includeCount + 1; includeStartIdx++) {
            addSufficientSearchBuilderRecursively(searchCombinationBuilders, includeCount, 0, targetIngredients.subList(includeStartIdx, targetIngredients.size()));
        }

        return searchCombinationBuilders;
    }

    private void addSufficientSearchBuilderRecursively(List<BooleanBuilder> builders,
                                                       int includeCount,
                                                       int includedCount,
                                                       List<RecipeIngredient> targetIngredients) {
        if (includedCount >= includeCount) {
            recursiveBuilder.and(eqSameIngredientSize(includedCount));
            builders.add(recursiveBuilder);
            recursiveBuilder = new BooleanBuilder();

            return;
        }

        for (int i = 0; i < targetIngredients.size(); i++) {
            recursiveBuilder.and(containIngredient(targetIngredients.get(i)));
            includedCount++;

            addSufficientSearchBuilderRecursively(builders, includeCount, includedCount, targetIngredients.subList(i + 1, targetIngredients.size()));
        }
    }

    private BooleanBuilder generateInsufficientSearchCombinationBuilder(List<RecipeIngredient> targetIngredients, int moreCount) {
        BooleanBuilder builder = containAllIngredient(targetIngredients);
        builder.and(eqSameIngredientSize(targetIngredients.size() + moreCount));

        return builder;
    }

    @Transactional
    public List<Recipe> get10RecipeWithOffset(Integer offset) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .offset(offset)
                .limit(10)
                .fetch();
    }

    private void shuffleSearchResult(List<Recipe> recipes) {
        Collections.shuffle(recipes);
    }

    private BooleanExpression eqSameIngredientSize(int size) {
        return QRecipe.recipe.ingredients.size().eq(size);
    }

    private BooleanExpression containIngredient(RecipeIngredient targetIngredient) {
        return QRecipe.recipe.ingredients.contains(targetIngredient);
    }

    private BooleanBuilder containAllIngredient(List<RecipeIngredient> targetIngredients) {
        BooleanBuilder builder = new BooleanBuilder();
        for (RecipeIngredient targetIngredient : targetIngredients) {
            builder.and(containIngredient(targetIngredient));
        }

        return builder;
    }
}
