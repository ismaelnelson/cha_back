package com.semillero.crakruk.mapper;

import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.dto.CommentDto;
import com.semillero.crakruk.dto.CommentPaginationDto;
import com.semillero.crakruk.dto.PaginationUrlDto;
import com.semillero.crakruk.mapper.util.IMapper;
import com.semillero.crakruk.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.semillero.crakruk.mapper.util.MapperUtil.streamListNonNull;

@Component
public class CommentMapper implements IMapper<Comment, CommentDto> {


    @Autowired
    ReplyMapper mapper;

    public Comment toEntity(CommentDto dto, UserModel user) {
        return Comment.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .user(user)
                .body(dto.getBody())
                .deleted(false)
                .build();
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .deleted(false)
                .build();
    }

    @Override
    public Comment toEntity(Long id, CommentDto dto) {
        return Comment.builder()
                .id(id)
                .title(dto.getTitle())
                .body(dto.getBody())
                .build();
    }

    @Override
    public CommentDto toDto(Comment entity) {
        ArrayList<String> likesUser = new ArrayList<>();
        entity.getLike().forEach(x -> likesUser.add(x.getUser()));
        Integer interactions;

        if (entity.getReply() == null){
             interactions = entity.getLike().size();
        }else {
             interactions = entity.getLike().size()+entity.getReply().size();
        }


        return CommentDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .userName(entity.getUser().getUser())
                .photo(entity.getUser().getPhoto())
                .body(entity.getBody())
                .likes(entity.getLike().size())
                .reply(mapper.toDtoList(entity.getReply()))
                .UsersLike(likesUser)
                .created(entity.getUpdated())
                .interactions(interactions)
                .build();
    }

    public void update(Comment entity,CommentDto dto){
        if(!(dto.getBody() == null)){
            entity.setBody(dto.getBody());
        }
    }

    @Override
    public List<CommentDto> toDtoList(List<Comment> list) {
        return streamListNonNull(list, this::toDto);
    }

    public CommentPaginationDto listNameDto(List<CommentDto> list, PaginationUrlDto dto) {
        return new CommentPaginationDto(dto, list);
    }


}
