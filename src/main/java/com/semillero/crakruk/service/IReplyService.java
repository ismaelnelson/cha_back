package com.semillero.crakruk.service;

import com.semillero.crakruk.dto.ReplyDto;
import com.semillero.crakruk.model.Reply;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IReplyService {

    ReplyDto createReply(ReplyDto dto , HttpServletRequest request,Long commentId);

    ReplyDto getById(Long id);

    void deleteReply(Long id);

    Reply findById(Long id);

    ReplyDto updateReply(Long id, ReplyDto dto);

    List<ReplyDto> getAllReply();

    public void likeReply (Long id,HttpServletRequest request);
}
