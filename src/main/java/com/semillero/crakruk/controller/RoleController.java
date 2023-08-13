package com.semillero.crakruk.controller;


import com.semillero.crakruk.dto.RoleDto;
import com.semillero.crakruk.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    IRoleService service;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody(required = true) RoleDto roleDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRole(roleDto));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id){
        service.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRoles());
    }
}
