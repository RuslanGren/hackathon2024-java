package com.ua.hackathon2024java.config;

import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.entity.Role;
import com.ua.hackathon2024java.services.RoleService;
import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StartupListener {
    private final UserService userService;
    private final RoleService roleService;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final List<String> regionRoles = List.of(
            "REGION_KYIV", "REGION_LVIV", "REGION_ODESA", "REGION_DNIPRO", "REGION_KHARKIV",
            "REGION_ZAPORIZHZHIA", "REGION_VINNYTSIA", "REGION_POLTAVA", "REGION_CHERNIHIV",
            "REGION_IVANO_FRANKIVSK", "REGION_LUHANSK", "REGION_DONETSK",
            "REGION_RIVNE", "REGION_KHERSON", "REGION_KHMELNYTSKYI", "REGION_CHERNIVTSI",
            "REGION_SUMY", "REGION_ZHYTOMYR", "REGION_CHERKASY", "REGION_MYKOLAIV",
            "REGION_TERNOPIL", "REGION_VOLYNSKA", "REGION_ZAKARPATSKA"
    );

    @EventListener(ContextRefreshedEvent.class)
    public void initializeRolesAndAdminUser() {
        try {
            userService.findById(1L);
        } catch (Exception e) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleService.save(adminRole);

            UserRequestDto adminUserRequest = new UserRequestDto(
                    adminUsername,
                    adminEmail,
                    adminPassword,
                    Set.of("ADMIN")
            );
            userService.createNewUser(adminUserRequest);

            for (String regionRole : regionRoles) {
                if (roleService.findByName(regionRole).isEmpty()) {
                    Role role = new Role();
                    role.setName(regionRole);
                    roleService.save(role);
                }
            }
        }
    }
}