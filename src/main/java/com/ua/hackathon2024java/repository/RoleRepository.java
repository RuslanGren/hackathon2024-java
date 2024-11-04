package com.ua.hackathon2024java.repository;

import com.ua.hackathon2024java.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Set<Role>> findAllByNameIn(Set<String> names);
}
