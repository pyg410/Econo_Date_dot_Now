package com.example.Datenow.DTO.CommentDto;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CommentRequestDto {

    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
