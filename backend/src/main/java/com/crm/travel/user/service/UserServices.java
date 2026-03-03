package com.crm.travel.user.service;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.dto.response.PermissionsResponse;
import com.crm.travel.user.dto.response.RolesResponse;
import com.crm.travel.user.dto.response.UserEntityResponse;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    User saveUser(UserRequest request);

    @Transactional
    void changePassword(String email, ChangePasswordRequest req);

    @Transactional
    String forgetPassword(String email, ChangePasswordRequest req);

    @Transactional(readOnly = true)
    Page<UserEntityResponse> findAll(Pageable pageable);

    List<OrgUnitTypeResponse> getLevels();

    List<RolesResponse> getRoles();

    List<PermissionsResponse> getPermissions();

    // List<User> findByDepartment(Department department);
    @Transactional(readOnly = true)
    Page<UserEntityResponse> getDescendantUsers(User currentUser, Pageable pageable, String keyword);

    @Transactional
    UserEntityResponse updateUser(UserRequest request);

}
