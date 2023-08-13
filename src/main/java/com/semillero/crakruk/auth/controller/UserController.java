package com.semillero.crakruk.auth.controller;

import com.semillero.crakruk.auth.dto.UserDto;
import com.semillero.crakruk.auth.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUser(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserBoolean(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> enableUser (@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.enableUser(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/superUser/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateRole(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
