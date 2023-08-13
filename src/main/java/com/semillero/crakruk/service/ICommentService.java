package com.semillero.crakruk.service;



import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.dto.CommentDto;
import com.semillero.crakruk.dto.CommentPaginationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICommentService {
    CommentDto createComment(CommentDto dto , HttpServletRequest request);

    CommentDto getById(Long id);

    void deleteComment(Long id);

    CommentDto updateComment(Long id, CommentDto dto);

    CommentPaginationDto getAllComments(Integer page);

    List<CommentDto> getPopularComments();

    void likeComment(Long id,HttpServletRequest request);
}
