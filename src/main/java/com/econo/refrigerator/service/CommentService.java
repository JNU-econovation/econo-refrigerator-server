package com.econo.refrigerator.service;

import com.econo.refrigerator.domain.Comment.Comment;
import com.econo.refrigerator.domain.Comment.CommentRepository;
import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecipeService recipeService;

    public Long create(Long recipeId, CommentDto commentDto) {
        Recipe recipe = recipeService.read(recipeId);
        Comment commentEntity = Comment.builder()
                .author(commentDto.getAuthor())
                .content(commentDto.getContent())
                .password(commentDto.getPassword())
                .recipe(recipe)
                .build();

        return commentRepository.save(commentEntity).getId();
    }

    public void update(Long commentId, String password, CommentDto commentDto) throws IllegalArgumentException{
        Comment comment = commentRepository.getOne(commentId);
        if (comment.checkPassword(password)) {
            comment.update(commentDto);
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
    }

    public void delete(Long id, String password) throws IllegalArgumentException{
        Comment comment = commentRepository.getOne(id);
        if (comment.checkPassword(password)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
    }
}
