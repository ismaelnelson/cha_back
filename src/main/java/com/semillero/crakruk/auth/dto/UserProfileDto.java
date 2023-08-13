package com.semillero.crakruk.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private String user;
    @Email
    private String email;
    private String photo;
    private String name;
    private String lastName;
    private String role;

}