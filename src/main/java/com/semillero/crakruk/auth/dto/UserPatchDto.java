package com.semillero.crakruk.auth.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {

    private String user;
    private String photo;
    private String name;
    private String lastName;

}
