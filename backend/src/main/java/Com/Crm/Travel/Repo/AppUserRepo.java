package Com.Crm.Travel.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.Crm.Travel.Entities.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {
    boolean existsByEmail(String userEmail);

    AppUser findByEmail(String useremail);

    Optional<AppUser> findByName(String name);

}
