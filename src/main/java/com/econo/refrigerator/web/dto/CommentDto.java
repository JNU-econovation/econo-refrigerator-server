package com.econo.refrigerator.web.dto;

import com.econo.refrigerator.domain.Comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    private String author;
    private String content;
    private Integer password;


    @Builder
    public CommentDto(String author, String content, Integer password) {
        this.author = author;
        this.content = content;
        this.password = password;
    }

    public Comment toEntity() {
        return Comment.builder()
                .author(author)
                .content(content)
                .password(password)
                .build();
    }
}
