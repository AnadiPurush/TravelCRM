package Com.Crm.Travel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.Crm.Travel.Entities.Quaries;

@Repository
public interface QuariesRepo extends JpaRepository<Quaries, Long> {

}
