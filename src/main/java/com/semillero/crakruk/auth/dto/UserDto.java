package com.semillero.crakruk.auth.dto;


import com.semillero.crakruk.model.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotNull(message = "{error.empty_field}")
    private String user;
    @NotNull(message = "{error.empty_field}")
    @Email
    private String email;

    private String name;
    private String lastName;

    private String photo;

    @NotNull(message = "{error.empty_field}")
    private String password;


    private Role role;

    private LocalDate updated;

    private LocalDate created;

    private Boolean deleted;

    @Builder
    public UserDto(String user,String photo, String email, String password, Role role) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.role = role;
    }
}
