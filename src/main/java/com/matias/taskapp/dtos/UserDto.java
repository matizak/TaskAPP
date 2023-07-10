package com.matias.taskapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Por favor elija un nombre.")
    private String name;

    @NotEmpty(message = "Por favor elija un apellido.")
    private String surname;
    @NotEmpty(message = "Por favor indique un correo.")
    private String username;

    @NotEmpty(message = "Por favor asigne una clave.")
    private String password;
}
