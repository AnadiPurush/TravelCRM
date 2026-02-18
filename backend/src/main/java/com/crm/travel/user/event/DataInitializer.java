package com.crm.travel.user.event;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import com.crm.travel.user.service.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadSuperAdmin(@Lazy UserServices repo) {
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
                admin.setManager(true);
                admin.setEnabled(true);
                admin.setLocked(false);
                admin.setRole(Roles.SUPER_ADMIN);
                admin.setDepartment(Department.GLOBAL);
                admin.setPermissions(Set.of(
                        UserPermissions.EXPENSE_ADD,
                        UserPermissions.EXPENSE_VIEW,
                        UserPermissions.INVOICE_CREATE,
                        UserPermissions.LEDGER_VIEW,
                        UserPermissions.PERMISSION_ASSIGN,

                        UserPermissions.QUOTE_UPDATE,

                        UserPermissions.QUERY_CREATE,

                        UserPermissions.TICKET_UPLOAD,
                        UserPermissions.TRIP_ASSIGN,

                        UserPermissions.USER_CREATE,
                        UserPermissions.USER_MANAGE,
                        UserPermissions.SYSTEM_CONFIG,
                        UserPermissions.VISA_PROCESS));
                repo.saveSuperAdmin(admin);
            }
        };

    }

}