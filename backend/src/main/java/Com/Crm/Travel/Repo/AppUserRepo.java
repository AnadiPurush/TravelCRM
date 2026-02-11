package Com.Crm.Travel.Repo;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.common.enums.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
	boolean existsByEmail(String userEmail);

	AppUser findByEmail(String useremail);

	Optional<AppUser> findByName(String name);

	List<AppUser> findUserByEmail(String email);

	List<AppUser> findByDepartment(Department department);
}
