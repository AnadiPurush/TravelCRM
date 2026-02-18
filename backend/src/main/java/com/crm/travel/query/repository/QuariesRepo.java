package com.crm.travel.query.repository;

import com.crm.travel.query.domain.Queries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuariesRepo extends JpaRepository<Queries, Long> {

        // Find by assigned user ID with pagination
        Page<Queries> findByAssignmentHelper_User_Id(Long id, Pageable pageable);

        // Find by assigned user ID and status with pagination
        Page<Queries> findByAssignmentHelper_idAndQueriesStatus(
                        Long id,
                        String quariesStatus,
                        Pageable pageable);

        // Find by assigned user ID and priority with pagination
        Page<Queries> findByAssignmentHelper_AndQueriesPriority(
                        Long id,
                        String quariesPriority,
                        Pageable pageable);


}
