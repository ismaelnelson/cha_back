package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.auth.service.IUserService;
import com.semillero.crakruk.dto.ReplyDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.mapper.ReplyMapper;
import com.semillero.crakruk.model.Comment;
import com.semillero.crakruk.model.Reply;
import com.semillero.crakruk.repository.CommentRepository;
import com.semillero.crakruk.repository.ReplyRepository;
import com.semillero.crakruk.service.IReplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReplyService implements IReplyService {
    private final ReplyRepository repository;
    private final ReplyMapper mapper;
    private final IUserService userService;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public ReplyDto createReply(ReplyDto dto , HttpServletRequest request,Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment", "id", commentId));
        UserModel user = userService.getUser(request);
        UserModel userReceiver = userService.getUserByUserName(dto.getUserReceiver());
        Reply reply = mapper.toEntity(dto,user);
        reply.setReceiver(userReceiver);
        reply.setLike(new ArrayList<>());
        repository.save(reply);
        comment.addReply(reply);
        commentRepository.save(comment);
        return mapper.toDto(reply);
    }

    @Override
    public ReplyDto getById(Long id) {
        Reply reply = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reply", "id", id));
        return mapper.toDto(reply);
    }

    @Override
    public void deleteReply(Long id) {

        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Reply", "id", id);
        }
        repository.deleteById(id);
    }

    @Override
    public Reply findById(Long id){
        Optional<Reply> reply = repository.findById(id);
        if (reply.isEmpty())throw new EntityNotFoundException("Reply", "id", id);
        return reply.get();
    }

    @Override
    public ReplyDto updateReply(Long id, ReplyDto dto) {
        Reply reply = this.findById(id);
        mapper.update(reply,dto);
        repository.save(reply);
        return mapper.toDto(reply);
    }

    @Override
    public List<ReplyDto> getAllReply() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public void likeReply (Long id,HttpServletRequest request){
        UserModel user = userService.getUser(request);
        Reply reply = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reply", "id", id));
        if (!(reply.getLike().contains(user))){
            reply.addLike(user);
        }else{
            reply.getLike().remove(user);
        }

        repository.save(reply);
    }
}
