package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.dto.RoleDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.mapper.RoleMapper;
import com.semillero.crakruk.model.Role;
import com.semillero.crakruk.repository.RoleRepository;
import com.semillero.crakruk.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    @Override
    public RoleDto createRole(RoleDto dto) {
        Role role = mapper.toEntity(dto);
        roleRepository.save(role);
        return mapper.toDto(role);
    }

    @Override
    public void deleteRole(Long id) {

        if (!roleRepository.existsById(id)){
            throw new EntityNotFoundException("Role", "id", id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return mapper.toDtoList(roleRepository.findAll());
    }
}
