package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Set<Role> findRolesByName(Set<String> roles);
    Optional<Role> findByName(String name);
    void save(Role role);
}
