package com.projeto.ms_auth.dtos;

import java.util.UUID;

public record LoginResponseDTO(String token, String tipo, String role, String nome, UUID professorId) {
}
