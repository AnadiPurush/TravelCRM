package com.crm.travel.user.service;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.enums.Department;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserServices {
    boolean getSuperAdmin(String userEmail);

    @Transactional
    void saveSuperAdmin(User user);

    User findUserByEmail(String userEmail);

    boolean isSuperAdmin(Authentication authentication);

    @Transactional
    boolean saveUser(UserRequest request);

    @Transactional
    void changePassword(String email, ChangePasswordRequest req);

    @Transactional
    String forgetPassword(String email, ChangePasswordRequest req);

    List<User> findByDepartment(Department department);

}
