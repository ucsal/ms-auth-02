package com.projeto.ms_auth.controller;

import com.projeto.ms_auth.domain.Role;
import com.projeto.ms_auth.dtos.LoginResponseDTO;
import com.projeto.ms_auth.dtos.UsuarioLoginDTO;
import com.projeto.ms_auth.dtos.UsuarioRegisterDTO;
import com.projeto.ms_auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UsuarioLoginDTO data) {
        LoginResponseDTO loginResponseDTO = usuarioService.login(data);
        return ResponseEntity.ok(loginResponseDTO);
    }

    // Endpoint público (ou para uso do ms-professor)
    @PostMapping("/register/professor")
    public ResponseEntity<?> registerProfessor(@RequestBody @Valid UsuarioRegisterDTO  data) {
        this.usuarioService.cadastrarUsuario(data);
        return ResponseEntity.ok().build();
    }
}
