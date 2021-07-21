package com.econo.refrigerator.domain.Recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class RecipeQueryRepository extends QuerydslRepositorySupport {

    private static final int ONCE_SEARCH_COUNT = 5;

    private final JPAQueryFactory queryFactory;

    public RecipeQueryRepository(JPAQueryFactory queryFactory) {
        super(Recipe.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Recipe> searchRecipesByIngredientsOnce(List<RecipeIngredient> targetIngredients) {

//        BooleanBuilder builder = new BooleanBuilder();
//        builder.and(QRecipe.recipe.ingredients.contains(targetIngredients.get(0)));
//        builder.and(QRecipe.recipe.ingredients.contains(targetIngredients.get(1)));
//        List<Recipe> searchedRecips = queryFactory
//                .selectFrom(QRecipe.recipe)
//                .where(builder)
//                .limit(ONCE_SEARCH_COUNT)
//                .fetch();
//        return searchedRecips;

        List<Recipe> searchResult = new ArrayList<>();

        for (int i = targetIngredients.size(); i > 0; i--) {
            if (searchResult.size() == ONCE_SEARCH_COUNT) {
                return searchResult;
            }

            List<BooleanBuilder> searchCombinationBuilders = generateAllSearchCombinationBuilders(targetIngredients, i);
            List<Recipe> combinationSearchResult = searchRecipeByBuilderUntillUnable(searchCombinationBuilders, ONCE_SEARCH_COUNT - searchResult.size());
            searchResult.addAll(combinationSearchResult);
        }

        return searchResult;
    }

    private List<Recipe> searchRecipeByBuilderUntillUnable(List<BooleanBuilder> builders, int maxSearchResultCount) {
        List<Recipe> searchResult = new ArrayList<>();

        for (BooleanBuilder builder : builders) {
            if (searchResult.size() == maxSearchResultCount) {
                shuffleSearchResult(searchResult);
                break;
            }

            List<Recipe> searchedRecips = queryFactory
                    .selectFrom(QRecipe.recipe)
                    .where(builder)
                    .limit(maxSearchResultCount - searchResult.size())
                    .fetch();
            searchResult.addAll(searchedRecips);
        }

        return searchResult;
    }

    private void shuffleSearchResult(List<Recipe> recipes) {
        Collections.shuffle(recipes);
    }


    private List<BooleanBuilder> generateAllSearchCombinationBuilders(List<RecipeIngredient> targetIngredients, int includeCount) {
        List<BooleanBuilder> searchCombinationBuilders = new ArrayList<>();

        for (int i = 0; i < targetIngredients.size() - includeCount + 1; i++) {
            addSearchBuilderRecursively(searchCombinationBuilders, new BooleanBuilder(), includeCount, 0, targetIngredients.subList(i, targetIngredients.size()));
        }

        return searchCombinationBuilders;
    }

    private void addSearchBuilderRecursively(List<BooleanBuilder> builders,
                                             BooleanBuilder builder,
                                             int includeCount,
                                             int includedCount,
                                             List<RecipeIngredient> targetIngredients) {
        if (includedCount >= includeCount) {
            builder.and(QRecipe.recipe.ingredients.size().eq(includedCount));
            builders.add(builder);
            builder = new BooleanBuilder();

            return;
        }

        for (int i = 0; i < targetIngredients.size(); i++) {
            builder.and(QRecipe.recipe.ingredients.contains(targetIngredients.get(i)));
            includedCount++;

            addSearchBuilderRecursively(builders, builder, includeCount, includedCount, targetIngredients.subList(i + 1, targetIngredients.size()));
        }
    }

}
