package com.semillero.crakruk.dto;

import com.semillero.crakruk.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String userName;

    private String photo;

    @NotNull(message = "{error.empty_field}")
    private String title;

    @NotNull(message = "{error.empty_field}")
    private String body;

    private Integer likes;

    private List<ReplyDto> reply;

    private List<String> UsersLike;

    private LocalDateTime created;

    private Integer interactions;
}
