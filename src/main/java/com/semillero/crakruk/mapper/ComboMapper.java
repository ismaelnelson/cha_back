package com.semillero.crakruk.mapper;

import com.semillero.crakruk.dto.ComboDto;
import com.semillero.crakruk.mapper.util.IMapper;
import com.semillero.crakruk.model.Combo;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.semillero.crakruk.mapper.util.MapperUtil.streamListNonNull;

@Component
public class ComboMapper implements IMapper<Combo, ComboDto> {
    @Override
    public Combo toEntity(ComboDto dto) {
        return Combo.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .image(dto.getImage())
                .link(dto.getLink())
                .deleted(false)
                .build();
    }

    @Override
    public Combo toEntity(Long id, ComboDto dto) {
        return Combo.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .image(dto.getImage())
                .link(dto.getLink())
                .build();
    }

    @Override
    public ComboDto toDto(Combo entity) {
        return ComboDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .image(entity.getImage())
                .link(entity.getLink())
                .build();
    }

    public void update(Combo entity,ComboDto dto){
        if(!(dto.getName() == null)){
            entity.setName(dto.getName());
        }
        if(!(dto.getDescription().isEmpty())){
            entity.setDescription(dto.getDescription());
        }
        if(!(dto.getImage() == null)){
            entity.setImage(dto.getImage());
        }
        if(!(dto.getPrice() == null)){
            entity.setPrice(dto.getPrice());
        }
        if(!(dto.getLink() == null)){
            entity.setLink(dto.getLink());
        }
    }

    @Override
    public List<ComboDto> toDtoList(List<Combo> list) {
        return streamListNonNull(list, this::toDto);
    }
}
