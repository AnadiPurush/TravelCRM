package com.crm.travel.query.service;

import com.crm.travel.query.domain.Queries;
import com.crm.travel.query.dto.QueriesDetailDTO;
import com.crm.travel.query.dto.request.QueryRequest;
import com.crm.travel.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface QuariesServices {

        // Save
        @Transactional
        boolean saveQuaries(QueryRequest helper);

        // Find by assigned user ID with pagination
        public Page<Queries> findByAppUser_id(Long id, int page, int size, String sortBy);

        // Find by assigned user ID and status with pagination
        public Page<Queries> getQueriesByUserWithFilters(
                        Long id,
                        String status,
                        int page,
                        int size,
                        String sortBy);

        // Find by assigned user ID and priority with pagination
        Page<Queries> findByAppUser_idAndQuariesPriority(
                        Long id,
                        String priority,
                        int page,
                        int size,
                        String sortBy);

        // Find by assigned user ID and filters

        Page<QueriesDetailDTO> findAllWithFilters(QueryRequest helper,
                                                  User user,
                                                  Pageable pageable);

        Queries findById(Long Id);

        String updateQuery(QueryRequest helper);

}
