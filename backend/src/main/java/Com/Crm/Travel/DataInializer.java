package Com.Crm.Travel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Services.appUserServices.AppUserServices;

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

                repo.saveSuperAdmin(admin);
            }
        };

    }

}