package Com.Crm.Travel;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Services.AppUserServices;
import Com.Crm.Travel.common.enums.AppUserPermissions;

@Configuration
public class DataInializer {

    private final PasswordEncoder passwordEncoder;

    public DataInializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadSuperAdmin(@Lazy AppUserServices repo) {
        return args -> {
            boolean superAdmin = repo.getSuperAdmin("superAdmin@gmail.com");
            if (superAdmin) {

                System.out.println("Super Admin Already Existed");

            } else {
                AppUser admin = new AppUser();
                admin.setName("super_Admin");
                admin.setEmail("superAdmin@gmail.com");
                admin.setPassword(passwordEncoder.encode("ChangeMe123"));
                admin.setSuperAdmin(true);
                admin.setManager(true);
                admin.setEnabled(true);
                admin.setLocked(false);

                admin.setPermissions(Set.of(
                        AppUserPermissions.EXPENSE_ADD,
                        AppUserPermissions.EXPENSE_VIEW,
                        AppUserPermissions.INVOICE_CREATE,
                        AppUserPermissions.LEDGER_VIEW,
                        AppUserPermissions.PERMISSION_ASSIGN,
                        AppUserPermissions.QUOTE_CREATE,
                        AppUserPermissions.QUOTE_UPDATE,
                        AppUserPermissions.QUERY_ASSIGN,
                        AppUserPermissions.QUERY_CREATE,
                        AppUserPermissions.QUERY_VIEW,
                        AppUserPermissions.TICKET_UPLOAD,
                        AppUserPermissions.TRIP_ASSIGN,
                        AppUserPermissions.TRIP_CREATE,
                        AppUserPermissions.USER_CREATE,
                        AppUserPermissions.USER_MANAGE));
                repo.saveSuperAdmin(admin);
            }
        };

    }

}