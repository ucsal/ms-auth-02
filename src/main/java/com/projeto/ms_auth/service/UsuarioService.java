package com.projeto.ms_auth.service;

import com.projeto.ms_auth.domain.Role;
import com.projeto.ms_auth.domain.Usuario;
import com.projeto.ms_auth.domain.UsuarioRepository;
import com.projeto.ms_auth.dtos.LoginResponseDTO;
import com.projeto.ms_auth.dtos.UsuarioLoginDTO;
import com.projeto.ms_auth.dtos.UsuarioRegisterDTO;
import com.projeto.ms_auth.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }


    public Usuario cadastrarUsuario(UsuarioRegisterDTO usuarioRegisterDTO) {
        existeUsuario(usuarioRegisterDTO.login());

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRegisterDTO.login());
        String passwordHash = passwordEncoder.encode(usuarioRegisterDTO.login());
        usuario.setSenha(passwordHash);
        usuario.setRole(Role.ROLE_PROFESSOR);

        Usuario savedUsuario = repository.save(usuario);
        return savedUsuario;
    }


    public LoginResponseDTO login(UsuarioLoginDTO usuarioLoginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.login(), usuarioLoginDTO.senha());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Usuario u = (Usuario) repository.findByEmail(usuarioLoginDTO.login());


        String token = tokenService.generateToken(usuario);


        return new LoginResponseDTO(token, "Bearer", usuario.getRole().name(), usuario.getEmail(), usuario.getId());
    }

    private void existeUsuario(String login) {
        Usuario usuario =(Usuario) repository.findByEmail(login);
        if (usuario != null) {
            throw new RuntimeException("Usuario com esse login já existe.");
        }
    }
}
