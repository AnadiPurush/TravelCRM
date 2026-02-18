package com.crm.travel.user.service;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public UserServicesImpl(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;

    }

    @Override
    public boolean getSuperAdmin(String userEmail) {
        return userRepo.existsByEmail(userEmail);
    }

    @Override
    @Transactional
    public void saveSuperAdmin(User user) {

        userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepo.findByEmail(userEmail);

    }

    @Override
    public boolean isSuperAdmin(Authentication authentication) {
        String name = authentication.getName();
        return userRepo.findByName(name).map(User::isSuperAdmin).orElse(false);

    }

    @Override
    @Transactional
    public boolean saveUser(UserRequest request) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = new User();
            boolean superAdmin = currentUser.isSuperAdmin();
            user.setName(request.name());
            user.setPassword(encoder.encode(request.Password()));
            user.setEmail(request.Email());
            user.setPermissions(request.permissions());
            user.setRole(request.role());

            if (superAdmin) {

                user.setManager(request.manager());
                user.setDepartment(request.department());
                userRepo.save(user);
                return true;
            } else {

                user.setDepartment(currentUser.getDepartment());
                userRepo.save(user);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest req) {
        try {
            User user = userRepo.findByEmail(email);
            if (encoder.matches(req.oldPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(req.newPassword()));

            } else {
                throw new InternalError("Please enter the correct old password");
            }
        } catch (UsernameNotFoundException e) {

            throw new UsernameNotFoundException("user not found");
        }

    }

    @Override
    @Transactional
    public String forgetPassword(String email, ChangePasswordRequest req) {
        try {
            User user = userRepo.findByEmail(email);
            // dirty checking password
            user.setPassword(encoder.encode(req.newPassword()));
            userRepo.saveAndFlush(user);
            return "Password changed successfully";
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("user not found");
        } catch (NullPointerException e) {
            throw new InternalError("Something went wrong while saving the password");
        }
    }

    @Override
    public List<User> findByDepartment(Department department) {
        return userRepo.findByDepartment(department);
    }

}
