package Com.Crm.Travel.Repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.Crm.Travel.Entities.Quaries;

@Repository
public interface QuariesRepo extends JpaRepository<Quaries, Long> {

        // Find by assigned user ID with pagination
        Page<Quaries> findByAppUser_id(Long id, Pageable pageable);

        // Find by assigned user ID and status with pagination
        Page<Quaries> findByAppUser_idAndQuariesStatus(
                        Long id,
                        String quariesStatus,
                        Pageable pageable);

        // Find by assigned user ID and priority with pagination
        Page<Quaries> findByAppUser_AndQuariesPriority(
                        Long id,
                        String quariesPriority,
                        Pageable pageable);

}
