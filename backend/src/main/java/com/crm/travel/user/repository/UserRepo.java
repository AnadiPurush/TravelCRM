package com.crm.travel.user.repository;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	boolean existsByEmail(String userEmail);

	User findByEmail(String userEmail);

	Optional<User> findByName(String name);

	List<User> findByAssignedQueries_Querie_SerialNumberIn(List<Long> Id);

	List<User> findByDepartment(Department department);

	List<User> findByDepartmentAndRoleOrderByIdAsc(Department department, Roles role);

}
