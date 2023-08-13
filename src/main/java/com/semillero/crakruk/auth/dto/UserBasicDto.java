package com.semillero.crakruk.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicDto {

    private Long id;

    @NotNull(message = "{error.empty_field}")
    private String user;

    @NotNull(message = "{error.empty_field}")
    private String email;


    private String photo;
}
