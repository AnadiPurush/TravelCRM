package com.crm.travel.user.service;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.dto.response.PermissionsResponse;
import com.crm.travel.user.dto.response.RolesResponse;
import com.crm.travel.user.dto.response.UserEntityResponse;
import com.crm.travel.user.mapper.UserMapper;
import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import com.crm.travel.user.organisationHierarchy.repository.OrgUnitRepo;
import com.crm.travel.user.repository.UserRepo;
import com.crm.travel.user.specification.OrgUnitsSpecification;
import com.crm.travel.user.specification.UserSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
	private final OrgUnitRepo orgUnitRepo;
	private final UserMapper userMapper;

	public UserServicesImpl(UserRepo userRepo, PasswordEncoder encoder, OrgUnitRepo orgUnitRepo,
							UserMapper userMapper) {
		this.userRepo = userRepo;
		this.encoder = encoder;

		this.orgUnitRepo = orgUnitRepo;
		this.userMapper = userMapper;
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
		return this.userRepo.findByName(name).map(User::isSuperAdmin).orElse(false);

    }

    @Override
    @Transactional
	public User saveUser(UserRequest request) {
		OrgUnit orgUnit = this.orgUnitRepo.findById(request.orgUnitId()).orElseThrow();
		User primaryManager = this.userRepo.findById(request.reportingManagerId()).orElseThrow();
		Set<User> secondaryManager = new HashSet<>(userRepo.findAllById(request.secondaryManagerIds()));
		return userRepo.save(userMapper.mapUserDtoToEntity(request, orgUnit, primaryManager, secondaryManager));

    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest req) {
		try {
			User user = userRepo.findByEmail(email);
			if (encoder.matches(req.oldPassword(), user.getPassword())) {
				if (encoder.matches(req.oldPassword(), req.newPassword())) {
					throw new InternalError("New password cannot be the same as the old password");
				}
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
	@Transactional(readOnly = true)
	public Page<UserEntityResponse> findAll(Pageable pageable) {
		Page<User> allUser = userRepo.findAll(pageable);
		return allUser.map(UserMapper::singleUserResponse);
	}

	@Override
	public List<OrgUnitTypeResponse> getLevels() {
		return UserMapper.mapOrgUnitsTypes();
	}

	@Override
	public List<RolesResponse> getRoles() {
		return UserMapper.mapRoles();
	}

	@Override
	public List<PermissionsResponse> getPermissions() {
		return UserMapper.mapPermissionsToDto();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserEntityResponse> getDescendantUsers(User currentUser, Pageable pageable, String keyword) {
		OrgUnit orgUnit = currentUser.getOrgUnit();
		Set<UUID> allSubUnitIds = new HashSet<>();
		OrgUnitsSpecification.collectDescendantIds(orgUnit, allSubUnitIds);
		Specification<User> specification = UserSpecifications.inOrgUnits(allSubUnitIds);
		Specification<User> specification1 = UserSpecifications.keyWord(keyword);
		Specification<User> finalSpecification = Specification.anyOf(specification1, specification);
		Page<User> all = userRepo.findAll(finalSpecification, pageable);
		return all.map(UserMapper::singleUserResponse);

	}
//    @Override
//    public List<User> findByDepartment(Department department) {
//        return userRepo.findBy(department);
//    }

	@Override
	@Transactional
	public UserEntityResponse updateUser(UserRequest request) {
		User user = userRepo.findByEmail(request.Email());
		user.setName(request.name());
		user.setEmail(request.Email());
		user.setOrgUnit(orgUnitRepo.findById(request.orgUnitId()).orElseThrow());
		user.setPermissions(request.permissions());
		user.setRole(request.role());
		user.setReportingManager(userRepo.findById(request.reportingManagerId()).orElseThrow());
		user.setSecondaryManagers(new HashSet<>(userRepo.findAllById(request.secondaryManagerIds())));
		return UserMapper.singleUserResponse(user);
    }

}
