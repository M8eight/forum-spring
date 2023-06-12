package com.chat.helper;

import com.chat.model.Role;
import com.chat.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoCreateRoles {
    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    void CreateRoles() {
        blankCreateRole(1L, "ROLE_USER");
        blankCreateRole(2L, "ROLE_ADMIN");
    }

    void blankCreateRole(Long roleId, String roleName) {
        if (roleRepository.findById(roleId).isEmpty()) {
            roleRepository.save(new Role(roleId, roleName));
        }
    }
}
