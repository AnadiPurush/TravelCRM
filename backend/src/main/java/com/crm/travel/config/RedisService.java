package com.crm.travel.config;

import com.crm.travel.query.domain.QueryAssignment;
import com.crm.travel.user.domain.User;
import com.crm.travel.query.domain.Queries;
import com.crm.travel.query.event.QueryCreatedEvent;
import com.crm.travel.user.repository.UserRepo;
import com.crm.travel.query.repository.QuariesRepo;
import com.crm.travel.query.repository.QueryAssignmentRepo;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
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

    public User getNextUserToAssign(Department department, Roles role) {
        loggerFactory.info("Transaction active: {}",
                org.springframework.transaction.support.TransactionSynchronizationManager
                        .isActualTransactionActive());
        String key = "ops_exec_counter:" + department + ":" + role;
        Long counter = redisTemplate.opsForValue().increment(key);
        if (counter == null) {
            throw new IllegalStateException("Counter increment failed");
        }

        List<User> executive = userRepo.findByDepartmentAndRoleOrderByIdAsc(department, role);
        if (executive.isEmpty()) {
            throw new RuntimeException("No" + department + role + "available");
        }
        int index = Math.floorMod(counter.intValue(), executive.size());
        return executive.get(index);

    }


    @Transactional
    public void assignNextAccountsUser(Long Id) {
        Queries queries = quariesRepo.findById(Id).orElseThrow();
        User accountsExec = this.getNextUserToAssign(Department.ACCOUNTS, Roles.ACCOUNTS_EXECUTIVE);
        User accountsManager = this.getNextUserToAssign(Department.ACCOUNTS, Roles.ACCOUNTS_MANAGER);
        QueryAssignment q1 = QueryAssignment.builder().querie(queries).user(accountsExec).department(Department.ACCOUNTS).roles(Roles.ACCOUNTS_EXECUTIVE).build();
        QueryAssignment q2 = QueryAssignment.builder().querie(queries).user(accountsManager).roles(Roles.ACCOUNTS_MANAGER).department(Department.ACCOUNTS).build();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)

    public void handle(QueryCreatedEvent.SalesEvent event) {
        Queries queries = quariesRepo.findById(event.Id()).orElseThrow();

        User salesExec = this.getNextUserToAssign(Department.SALES, Roles.SALES_EXECUTIVE);
        User salesManager = this.getNextUserToAssign(Department.SALES, Roles.SALES_MANAGER);
//        User opsExec = this.getNextUserToAssign(Department.OPERATIONS, Roles.OPERATIONS_EXECUTIVE);
//        User opsManager = this.getNextUserToAssign(Department.OPERATIONS, Roles.OPERATIONS_MANAGER);

        QueryAssignment q1 = QueryAssignment.builder().querie(queries).user(salesExec).roles(Roles.SALES_EXECUTIVE).department(Department.SALES).build();
        QueryAssignment q2 = QueryAssignment.builder().querie(queries).user(salesManager).roles(Roles.SALES_MANAGER).department(Department.SALES).build();
//        QueryAssignment q3 = QueryAssignment.builder().querie(quaries).user(opsExec).roles(Roles.OPERATIONS_EXECUTIVE).department(Department.OPERATIONS).build();
//        QueryAssignment q4 = QueryAssignment.builder().querie(quaries).user(opsManager).roles(Roles.OPERATIONS_MANAGER).department(Department.OPERATIONS).build();
        List<QueryAssignment> saveEntity = List.of(q1, q2);
        System.out.println("Is synchronization active: " +
                TransactionSynchronizationManager.isSynchronizationActive());

        loggerFactory.info("Saving {} assignments", saveEntity.size());
        repo.saveAll(saveEntity);
        repo.flush();
        loggerFactory.info("Assignments persisted");

    }


}

