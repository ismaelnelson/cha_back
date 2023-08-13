package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.auth.repository.UserRepository;
import com.semillero.crakruk.auth.service.IUserService;
import com.semillero.crakruk.dto.CommentDto;
import com.semillero.crakruk.dto.CommentPaginationDto;
import com.semillero.crakruk.dto.ReplyDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.exeption.NullListException;
import com.semillero.crakruk.exeption.PaginationSizeOutOfBoundsException;
import com.semillero.crakruk.mapper.CommentMapper;
import com.semillero.crakruk.model.Comment;
import com.semillero.crakruk.repository.CommentRepository;
import com.semillero.crakruk.service.ICommentService;
import com.semillero.crakruk.util.pagination.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final IUserService userService;



    @Override
    @Transactional
    public CommentDto createComment(CommentDto dto , HttpServletRequest request) {

        UserModel user = userService.getUser(request);
        Comment comment = mapper.toEntity(dto,user);
        comment.setLike(new ArrayList<>());
        commentRepository.save(comment);
        return mapper.toDto(comment);
    }

    @Override
    public CommentDto getById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
        return mapper.toDto(comment);
    }

    @Override
    public void deleteComment(Long id) {

        if (!commentRepository.existsById(id)){
            throw new EntityNotFoundException("Comment", "id", id);
        }
        commentRepository.deleteById(id);
    }

    public Comment findById(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty())throw new EntityNotFoundException("Comment", "id", id);
        return comment.get();
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto dto) {
        Comment comment = this.findById(id);
        mapper.update(comment,dto);
        commentRepository.save(comment);
        return mapper.toDto(comment);
    }

    @Override
    public CommentPaginationDto getAllComments(Integer page) {

        int auxiliar = commentRepository.countComments();
        int pageNumber = PaginationUtil.resolvePageNumber(page);
        int maximumPageNumber = ((auxiliar / 5)+1) ;

        if (pageNumber > maximumPageNumber) {
            throw new PaginationSizeOutOfBoundsException("error.pagination_size");
        }

        Pageable pageable = PageRequest.of(pageNumber, 5,Sort.by("created").descending());
        List<CommentDto> dtoList = mapper.toDtoList(commentRepository.findAllCommentsRecientes(pageable));
        // dtoList.forEach(x -> Collections.sort(x.getReply(), Comparator.comparing(ReplyDto::getCreated)));

        return mapper.listNameDto(dtoList, PaginationUtil.getPreviousAndNextPage(pageNumber, maximumPageNumber));
    }

    @Override
    public List<CommentDto> getPopularComments() {
        List<CommentDto> dtoList = mapper.toDtoList(commentRepository.findAllActiveUser(null));
        dtoList.forEach(x -> Collections.sort(x.getReply(), Comparator.comparing(ReplyDto::getCreated)));

        Collections.sort(dtoList, Comparator.comparing(CommentDto::getInteractions).reversed());

        if (dtoList.size() > 6){
            return dtoList.subList(0,6);
        }
        return dtoList;
    }

    @Override
    public void likeComment(Long id,HttpServletRequest request){
        UserModel user = userService.getUser(request);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
        if (!(comment.getLike().contains(user))){
            comment.addLike(user);
        }else {
            comment.getLike().remove(user);
        }
        commentRepository.save(comment);
    }
}
