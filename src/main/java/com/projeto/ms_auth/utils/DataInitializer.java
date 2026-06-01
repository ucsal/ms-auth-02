package com.projeto.ms_auth.utils;

import com.projeto.ms_auth.domain.Role;
import com.projeto.ms_auth.domain.Usuario;
import com.projeto.ms_auth.domain.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (usuarioRepository.findByEmail("admin@ucsal.edu") == null) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@ucsal.edu");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setRole(Role.ROLE_ADMIN);
            usuarioRepository.save(admin);
            System.out.println(">>> ADMIN CRIADO. HASH: " + admin.getSenha());
        } else {
            System.out.println(">>> ADMIN JÁ EXISTE.");
        }
    }
}
