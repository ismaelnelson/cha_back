package com.semillero.crakruk.auth.controller;



import com.semillero.crakruk.auth.dto.*;
import com.semillero.crakruk.auth.service.CustomUserDetailsService;
import com.semillero.crakruk.auth.service.IUserService;
import com.semillero.crakruk.auth.service.JwtUtils;
import com.semillero.crakruk.model.Role;
import com.semillero.crakruk.repository.RoleRepository;
import com.semillero.crakruk.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(HttpServletRequest request) {

        UserProfileDto dto = userService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserBasicDto user = userService.signup(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(HttpServletRequest request, @Valid @RequestBody UserPatchDto updates){
        try {
            UserProfileDto response = userService.updateUser(request,updates);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) throws Exception {

        return ResponseEntity.ok(userService.generateToken(authRequest));
    }


    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @Valid @RequestBody ResetPasswordDto passwordDto){
        try {
            userService.resetPassword(request,passwordDto);
            return ResponseEntity.status(HttpStatus.OK).body("The password was changed successfully!");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/role/{userId}/{roleId}")
    public ResponseEntity<?> updateRole(@PathVariable(value = "userId") Long userId,@PathVariable(value = "roleId") Long roleId){

        UserProfileDto response = userService.updateRole(userId,roleId);

        if (response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el rol.");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
