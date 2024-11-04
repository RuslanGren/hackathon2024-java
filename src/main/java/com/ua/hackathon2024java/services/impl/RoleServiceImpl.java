package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.entity.Role;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.exceptions.DatabaseException;
import com.ua.hackathon2024java.repository.RoleRepository;
import com.ua.hackathon2024java.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Role> findRolesByName(Set<String> roles) {
        Set<Role> foundRoles = roleRepository.findAllByNameIn(roles)
                .orElseThrow(() -> new BadRequestException("One or more roles not found"));

        if (foundRoles.size() != roles.size()) {
            throw new BadRequestException("One or more roles not found");
        }

        return foundRoles;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Role role) {
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }
}
