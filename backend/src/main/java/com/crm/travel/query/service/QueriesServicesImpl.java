package com.crm.travel.query.service;

import com.crm.travel.config.RedisService;
import com.crm.travel.query.comment.domain.QueriesComment;
import com.crm.travel.query.comment.dto.QueriesCommentDTO;
import com.crm.travel.query.comment.service.QueriesCommentService;
import com.crm.travel.query.domain.Queries;
import com.crm.travel.query.dto.QueriesDetailDTO;
import com.crm.travel.query.dto.request.QueryRequest;
import com.crm.travel.query.event.QueryCreatedEvent;
import com.crm.travel.query.repository.QuariesRepo;
import com.crm.travel.query.repository.QueryAssignmentRepo;
import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.response.AssignedUserDTO;
import com.crm.travel.user.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueriesServicesImpl implements QuariesServices {

    private final QuariesRepo quariesRepo;
    private final UserRepo userRepo;
    private final QueriesCommentService queriesCommentService;
    private final RedisService redisService;
    private final QueryAssignmentRepo queryAssignmentRepo;
    private final ApplicationEventPublisher applicationEventPublisher;


    @SuppressWarnings("null")
    @Override
    @Transactional
    public boolean saveQuaries(QueryRequest helper) {

        Queries queries = Queries.builder().requesterName(helper.requesterName()).contactNo(helper.contactNo())
                .email(helper.email()).destination(helper.destination()).fromLocation(helper.fromLocation())
                .fromDate(helper.fromDate()).toDate(helper.toDate()).quotedPrice(helper.quotedPrice())
                .requiredServices(helper.requiredServices()).queriesStatus(helper.queriesStatus())
                .queriesPriority(helper.queriesPriority()).build();
        quariesRepo.save(queries);
        applicationEventPublisher.publishEvent(
                new QueryCreatedEvent.SalesEvent(queries.getSerialNumber())
        );


        return true;

    }

    @Override
    public Page<Queries> findByAppUser_id(Long Id, int page, int size, String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAssignmentHelper_User_Id(Id, pageable1);
    }

    @Override
    public Page<Queries> getQueriesByUserWithFilters(Long userId, String status, int page, int size, String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAssignmentHelper_idAndQueriesStatus(userId, status, pageable1);

    }

    @Override
    public Page<Queries> findByAppUser_idAndQuariesPriority(Long userId, String priority, int page, int size,
                                                            String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAssignmentHelper_AndQueriesPriority(userId, priority, pageable1);

    }

    @Transactional
    @Override
    public Page<QueriesDetailDTO> findAllWithFilters(QueryRequest helper, User user, Pageable pageable) {

        Page<Queries> queries;

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
        Queries.QueriesBuilder builder = Queries.builder().destination(helper.destination())
                .queriesStatus(helper.queriesStatus()).queriesPriority(helper.queriesPriority())
                .requesterName(helper.requesterName());

        if (!user.isSuperAdmin()) {
            builder.assignmentHelper(queryAssignmentRepo.findUserByUser(user));
        }
        Queries filterCriteria = builder.build();
        Example<Queries> example = Example.of(filterCriteria, matcher);

        queries = quariesRepo.findAll(example, pageable);
        List<Long> queriesId = queries.stream().map(Queries::getSerialNumber).toList();
        List<QueriesComment> commentByQueryId = queriesCommentService.findByQuarie_SerialNumberIn(queriesId);
        Map<Long, List<QueriesComment>> mapCommentsByQueryId = commentByQueryId.stream()
                .collect(Collectors.groupingBy(c -> c.getQuarie().getSerialNumber()));
        List<User> assignedUsers = userRepo.findByAssignedQueries_Querie_SerialNumberIn(queriesId);
        Map<Long, List<User>> usersByQueryId =
                assignedUsers.stream()
                        .flatMap(u ->
                                u.getAssignedQueries().stream()
                                        .map(query ->
                                                Map.entry(query.getQuerie().getSerialNumber(), u)))
                        .collect(Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                        ));


        return queries.map(query -> {
            List<QueriesComment> comments = mapCommentsByQueryId.getOrDefault(query.getSerialNumber(), List.of());
            List<QueriesCommentDTO> commentDTOS = comments.stream().map(c -> new QueriesCommentDTO(c.getId(),
                    c.getCommentText(), c.getCreatedAt(), c.getModifiedAt(), c.getUser().toString())).toList();
            List<User> user1 = usersByQueryId.getOrDefault(query.getSerialNumber(), List.of());
            List<AssignedUserDTO> assignedUserDTOS =
                    user1.stream()
                            .map(u -> AssignedUserDTO.builder()
                                    .displayName(user.getName())
                                    .email(user.getEmail())
                                    .department(user.getDepartment())
                                    .role(user.getRole())
                                    .build())
                            .toList();
            return QueriesDetailDTO.builder().requesterName(query.getRequesterName())
                    .destination(query.getDestination()).fromLocation(query.getFromLocation())
                    .fromDate(query.getFromDate()).toDate(query.getToDate()).quotedPrice(query.getQuotedPrice())
                    .requiredServices(query.getRequiredServices()).createdAt(query.getCreatedAt())
                    .queriesStatus(query.getQueriesStatus()).queriesPriority(query.getQueriesPriority())
                    .serialNumber(query.getSerialNumber()).contactNo(query.getContactNo()).email(query.getEmail())
                    .queriesCommentDTOS(commentDTOS).queryAssignmentHelper(assignedUserDTOS).build();
        });

    }

    @Override
    public Queries findById(Long Id) {
        return quariesRepo.findById(Id).orElseThrow(() -> new EntityNotFoundException("Query By the Id Not Found"));
    }

    @Override
    public String updateQuery(QueryRequest helper) {
        Queries queries = quariesRepo.findById(helper.serialNumber()).orElseThrow();
        queries.setQueriesStatus(helper.queriesStatus());
        queries.setQueriesPriority(helper.queriesPriority());
        queries.setContactNo(helper.contactNo());
        queries.setEmail(helper.email());
        queries.setFromDate(helper.fromDate());
        queries.setToDate(helper.toDate());
        queries.setQuotedPrice(helper.quotedPrice());
        queries.setRequesterName(helper.requesterName());
        queries.setDestination(helper.destination());
        queries.setQuotedPrice(helper.quotedPrice());
        queries.setFromLocation(helper.fromLocation());
        return "Recent Changes have been updated";
    }

}
