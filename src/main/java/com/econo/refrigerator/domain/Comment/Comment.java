package com.econo.refrigerator.domain.Comment;

import com.econo.refrigerator.domain.Recipe.Recipe;
import com.econo.refrigerator.web.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Recipe recipe;


    @Builder
    public Comment(String author, String content, Integer password, Recipe recipe) {
        this.author = author;
        this.content = content;
        this.password = password;
        this.recipe = recipe;
    }


    public void update(CommentDto commentDto) {
        this.author = commentDto.getAuthor();
        this.content = commentDto.getContent();
    }

    public boolean checkPassword(Integer password) {
        return this.password == password;
    }
}
