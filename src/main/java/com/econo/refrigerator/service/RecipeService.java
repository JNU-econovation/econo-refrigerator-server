package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.domain.Recipe.RecipeRepository;
import com.econo.refrigerator.web.dto.RecipeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Long create(RecipeSaveDto recipeSaveDto) {
        return recipeRepository.save(recipeSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, RecipeSaveDto recipeSaveDto) {
        Recipe targetRecipe = read(id);
        targetRecipe.update(recipeSaveDto);

        return id;
    }

    public Recipe read(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
    }

    public void delete(Long id) {
        Recipe targetRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 없습니다. id = " + id));
        recipeRepository.delete(targetRecipe);
    }
}
