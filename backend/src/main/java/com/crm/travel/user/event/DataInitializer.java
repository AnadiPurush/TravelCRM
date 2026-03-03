package com.crm.travel.user.event;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import com.crm.travel.user.organisationHierarchy.repository.OrgUnitRepo;
import com.crm.travel.user.service.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadSuperAdmin(@Lazy UserServices repo, OrgUnitRepo orgUnitRepo) {
        UUID id = UUID.fromString("1f532862-4990-44f1-aa64-3b3e1410cb35");
        Set<UserPermissions> allPermission =
                EnumSet.allOf(UserPermissions.class);
        return args -> {
            boolean superAdmin = repo.getSuperAdmin("superAdmin@gmail.com");
            if (superAdmin) {
                System.out.println("Super Admin Already Existed");
            } else {
                User admin = new User();
                admin.setName("super_Admin");
                admin.setEmail("superAdmin@gmail.com");
                admin.setPassword(passwordEncoder.encode("ChangeMe123"));
                admin.setSuperAdmin(true);
                admin.setEnabled(true);
                admin.setLocked(false);
                admin.setReportingManager(null);
                admin.setOrgUnit(orgUnitRepo.findById(id).orElseThrow());
                admin.setRole(Roles.CEO);
                admin.setPermissions(allPermission);
                repo.saveSuperAdmin(admin);

            }
        };

    }

}