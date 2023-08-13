package com.semillero.crakruk.mapper;


import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.dto.ReplyDto;
import com.semillero.crakruk.mapper.util.IMapper;
import com.semillero.crakruk.model.Reply;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.semillero.crakruk.mapper.util.MapperUtil.streamListNonNull;

@Component
public class ReplyMapper implements IMapper<Reply, ReplyDto> {

    public Reply toEntity(ReplyDto dto, UserModel user){
        return Reply.builder()
                .user(user)
                .body(dto.getBody())
                .deleted(false)
                .build();
    }
    @Override
    public Reply toEntity(ReplyDto dto) {
        return null;
    }

    @Override
    public Reply toEntity(Long id, ReplyDto dto) {
        return null;
    }

    @Override
    public ReplyDto toDto(Reply entity) {
        ArrayList<String> likesUser = new ArrayList<>();
        entity.getLike().forEach(x -> likesUser.add(x.getUser()));
        return ReplyDto.builder()
                .id(entity.getId())
                .userName(entity.getUser().getUser())
                .photo(entity.getUser().getPhoto())
                .userReceiver(entity.getReceiver().getUser())
                .body(entity.getBody())
                .likes(entity.getLike().size())
                .UsersLike(likesUser)
                .created(entity.getUpdated())
                .build();
    }

    public void update(Reply entity , ReplyDto dto){
        if(!(dto.getBody() == null)){
            entity.setBody(dto.getBody());
        }
    }

    @Override
    public List<ReplyDto> toDtoList(List<Reply> list) {
        return streamListNonNull(list, this::toDto);
    }


}
