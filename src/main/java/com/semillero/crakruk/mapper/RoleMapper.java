package com.semillero.crakruk.mapper;

import com.semillero.crakruk.dto.RoleDto;
import com.semillero.crakruk.mapper.util.IMapper;
import com.semillero.crakruk.model.Role;
import org.springframework.stereotype.Component;

import static com.semillero.crakruk.mapper.util.MapperUtil.streamListNonNull;

import java.util.List;

@Component
public class RoleMapper implements IMapper<Role, RoleDto> {
    @Override
    public Role toEntity(RoleDto dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .deleted(false)
                .build();
    }

    @Override
    public Role toEntity(Long id, RoleDto dto) {
        return Role.builder()
                .id(id)
                .name(dto.getName())
                .build();
    }

    @Override
    public RoleDto toDto(Role entity) {
        return RoleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public List<RoleDto> toDtoList(List<Role> list) { return streamListNonNull(list, this::toDto); }
}
