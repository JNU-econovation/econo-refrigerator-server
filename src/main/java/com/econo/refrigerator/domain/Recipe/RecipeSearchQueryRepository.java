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

    public RecipeSearchQueryRepository(JPAQueryFactory queryFactory) {
        super(Recipe.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Recipe> searchSufficientRecipesByIngredientsOnce(List<RecipeIngredient> targetIngredients) {
        List<Recipe> searchResult = new ArrayList<>();

        for (int includeCount = targetIngredients.size(); includeCount > 0; includeCount--) {
            if (searchResult.size() == ONCE_SEARCH_COUNT) {
                break;
            }

            List<BooleanBuilder> searchCombinationBuilders = generateAllSearchCombinationBuilders(true, targetIngredients, includeCount);
            List<Recipe> combinationSearchResult = searchRecipeByBuilders(searchCombinationBuilders, ONCE_SEARCH_COUNT - searchResult.size());
            searchResult.addAll(combinationSearchResult);
        }

        return searchResult;
    }

    @Transactional
    public List<Recipe> searchInsufficientRecipesByIngredientsOnce(List<RecipeIngredient> targetIngredients) {
        List<Recipe> searchResult = new ArrayList<>();

        for (int includeCount = targetIngredients.size(); includeCount > 0; includeCount--) {
            List<BooleanBuilder> searchCombinationBuilders = generateAllSearchCombinationBuilders(false, targetIngredients, includeCount);

            for (BooleanBuilder builder : searchCombinationBuilders) {
                if (searchResult.size() == ONCE_SEARCH_COUNT) {
                    break;
                }

                List<Recipe> searchedInsufficientRecipes = searchRecipeByBuilder(builder);
                for (Recipe insufficientRecipe : searchedInsufficientRecipes) {
                    int moreCount = getMoreIngredientsCount(targetIngredients, insufficientRecipe.getIngredients());
                    if (moreCount > MORE_BUY_INGREDIENT_MAX_COUNT) {
                        continue;
                    }

                    searchResult.add(insufficientRecipe);
                }
            }
        }

        return searchResult;
    }

    private int getMoreIngredientsCount(List<RecipeIngredient> targetIngredients, List<RecipeIngredient> searchedIngredients) {
        int moreCount = 0;
        for (RecipeIngredient searchedIngredient : searchedIngredients) {
            if (targetIngredients.contains(searchedIngredient)) {
                continue;
            }

            moreCount++;
        }

        return moreCount;
    }

    private List<Recipe> searchRecipeByBuilder(BooleanBuilder builder) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .where(builder)
                .fetch();
    }

    private List<Recipe> searchRecipeByBuilder(BooleanBuilder builder, int searchLimit) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .where(builder)
                .limit(searchLimit)
                .fetch();
    }

    private List<Recipe> searchRecipeByBuilders(List<BooleanBuilder> builders, int searchLimit) {
        List<Recipe> searchResult = new ArrayList<>();

        for (BooleanBuilder builder : builders) {
            if (searchResult.size() == searchLimit) {
                shuffleSearchResult(searchResult);
                break;
            }

            searchResult.addAll(searchRecipeByBuilder(builder, searchLimit - searchResult.size()));
        }

        return searchResult;
    }

    private List<BooleanBuilder> generateAllSearchCombinationBuilders(boolean isSufficient, List<RecipeIngredient> targetIngredients, int includeCount) {
        List<BooleanBuilder> searchCombinationBuilders = new ArrayList<>();

        for (int includeStartIdx = 0; includeStartIdx < targetIngredients.size() - includeCount + 1; includeStartIdx++) {

            BooleanBuilder builder = new BooleanBuilder();
            builder.and(containIngredient(targetIngredients.get(includeStartIdx)));
            addSufficientSearchBuilderRecursively(isSufficient, searchCombinationBuilders, builder, targetIngredients.subList(includeStartIdx + 1, targetIngredients.size()), includeCount, 1);
        }

        return searchCombinationBuilders;
    }

    private void addSufficientSearchBuilderRecursively(boolean isSufficient,
                                                       List<BooleanBuilder> builders,
                                                       BooleanBuilder builder,
                                                       List<RecipeIngredient> targetIngredients,
                                                       int includeCount,
                                                       int includedCount) {
        if (includedCount >= includeCount) {

            if (isSufficient) {
                builder.and(eqSameIngredientSize(includedCount));
            }
            builders.add(builder);

            return;
        }

        for (int i = 0; i < targetIngredients.size(); i++) {

            BooleanBuilder prevBuilder = new BooleanBuilder(builder);

            builder.and(containIngredient(targetIngredients.get(i)));
            addSufficientSearchBuilderRecursively(isSufficient, builders, builder, targetIngredients.subList(i + 1, targetIngredients.size()), includeCount, includedCount + 1);

            builder = new BooleanBuilder(prevBuilder);
        }
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

    private BooleanExpression containIngredient(RecipeIngredient targetIngredient) {
        return QRecipe.recipe.ingredients.contains(targetIngredient);
    }

    private BooleanExpression eqSameIngredientSize(int size) {
        return QRecipe.recipe.ingredients.size().eq(size);
    }
}
