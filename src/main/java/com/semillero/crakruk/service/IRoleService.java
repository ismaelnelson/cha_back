package com.semillero.crakruk.service;

import com.semillero.crakruk.dto.RoleDto;

import java.util.List;

public interface IRoleService {

    RoleDto createRole(RoleDto dto);

    void deleteRole(Long id);

    List<RoleDto> getAllRoles();
}
