package com.crm.travel.query.repository;

import com.crm.travel.query.domain.QueryAssignment;
import com.crm.travel.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryAssignmentRepo extends JpaRepository<QueryAssignment, Long> {

    List<QueryAssignment> findUserByUser(User user);

    List<QueryAssignment> findByQuerie_SerialNumberIn(List<Long> ids);


}
