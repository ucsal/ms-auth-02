package com.projeto.ms_auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO
        (@NotBlank
            @Email
        String login,
@NotBlank String senha) {
}
