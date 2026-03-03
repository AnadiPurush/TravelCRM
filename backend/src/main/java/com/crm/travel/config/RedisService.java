package com.crm.travel.config;

import com.crm.travel.query.domain.Queries;
import com.crm.travel.query.domain.QueryAssignment;
import com.crm.travel.query.event.QueryCreatedEvent;
import com.crm.travel.query.repository.QuariesRepo;
import com.crm.travel.query.repository.QueryAssignmentRepo;
import com.crm.travel.user.domain.User;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@AllArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private final UserRepo userRepo;
    private final QuariesRepo quariesRepo;
    private final Logger loggerFactory = LoggerFactory.getLogger(this.getClass());
    private final QueryAssignmentRepo repo;

    public User getNextUserToAssign(Roles role) {
        loggerFactory.info("Transaction active: {}",
                org.springframework.transaction.support.TransactionSynchronizationManager
                        .isActualTransactionActive());
        String key = "ops_exec_counter:" + ":" + role;
        Long counter = redisTemplate.opsForValue().increment(key);
        if (counter == null) {
            throw new IllegalStateException("Counter increment failed");
        }

        List<User> executive = userRepo.findByRoleOrderByIdAsc(role);
        if (executive.isEmpty()) {
            throw new RuntimeException("No" + role + "available");
        }
        int index = Math.floorMod(counter.intValue(), executive.size());
        return executive.get(index);

    }


    @Transactional
    public void assignNextAccountsUser(Long Id) {
        Queries queries = quariesRepo.findById(Id).orElseThrow();
        User accountsExec = this.getNextUserToAssign(Roles.ACCOUNTS_EXECUTIVE),
                accountsManager = this.getNextUserToAssign(Roles.ACCOUNTS_MANAGER);
        QueryAssignment q1 = QueryAssignment.builder().querie(queries).user(accountsExec).roles(Roles.ACCOUNTS_EXECUTIVE).build(),
                q2 = QueryAssignment.builder().querie(queries).user(accountsManager).roles(Roles.ACCOUNTS_MANAGER).build();
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)

    public void handle(QueryCreatedEvent.SalesEvent event) {
        Queries queries = quariesRepo.findById(event.Id()).orElseThrow();

        User salesExec = this.getNextUserToAssign(Roles.SALES_EXECUTIVE);
        User salesManager = this.getNextUserToAssign(Roles.SALES_MANAGER);
        User opsExec = this.getNextUserToAssign(Roles.OPERATIONS_EXECUTIVE);
        User opsManager = this.getNextUserToAssign(Roles.OPERATIONS_MANAGER);

        QueryAssignment q1 = QueryAssignment.builder().querie(queries).user(salesExec).roles(Roles.SALES_EXECUTIVE).build();
        QueryAssignment q2 = QueryAssignment.builder().querie(queries).user(salesManager).roles(Roles.SALES_MANAGER).build();
        QueryAssignment q3 = QueryAssignment.builder().querie(queries).user(opsExec).roles(Roles.OPERATIONS_EXECUTIVE).build();
        QueryAssignment q4 = QueryAssignment.builder().querie(queries).user(opsManager).roles(Roles.OPERATIONS_MANAGER).build();
        List<QueryAssignment> saveEntity = List.of(q1, q2);
        System.out.println("Is synchronization active: " +
                TransactionSynchronizationManager.isSynchronizationActive());

        loggerFactory.info("Saving {} assignments", saveEntity.size());
        repo.saveAll(saveEntity);
        repo.flush();
        loggerFactory.info("Assignments persisted");

    }


}

