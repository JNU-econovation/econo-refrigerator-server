package com.econo.refrigerator.web;

import com.econo.refrigerator.service.CommentService;
import com.econo.refrigerator.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/recipe/{recipeId}/comment")
    public Long create(@PathVariable Long recipeId, @RequestBody CommentDto commentDto) {
        return commentService.create(recipeId, commentDto);
    }

    @PutMapping("/api/comment/{commentId}/{password}")
    public void update(@PathVariable Long commentId, @PathVariable Integer password, @RequestBody CommentDto commentDto) {
        commentService.update(commentId, password, commentDto);
    }

    @DeleteMapping("/api/comment/{commentId}/{password}")
    public void delete(@PathVariable Long commentId, @PathVariable Integer password) {
        commentService.delete(commentId, password);
    }
}
