package ru.jwt.springsecurityjwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jwt.springsecurityjwt.entities.Role;
import ru.jwt.springsecurityjwt.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}