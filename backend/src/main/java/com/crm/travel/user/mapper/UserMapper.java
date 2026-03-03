package com.crm.travel.user.mapper;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.dto.response.PermissionsResponse;
import com.crm.travel.user.dto.response.RolesResponse;
import com.crm.travel.user.dto.response.UserEntityResponse;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @file UserMapper.java
 * Author: Utsav
 * Date: 2/23/26
 *
 */
@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public static UserEntityResponse singleUserResponse(User user) {
        return UserEntityResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .email(user.getUsername())
                .role(user.getRole().getDisplayName())
                .managerName(user.getReportingManager().getName())
                .orgUnitName(user.getOrgUnit().getName())
                .orgUnitType(user.getOrgUnit().getOrgUnitTypes().getDisplayName())
                .secondaryManagers(user.getSecondaryManagers().stream().map(User::getName).toList())
                .permissions(user.getPermissions().stream().map(UserPermissions::getDisplayName).toList())
                .build();
    }

    public static List<OrgUnitTypeResponse> mapOrgUnitsTypes() {
        return Arrays.stream(OrgUnitTypes.values())
                .map(level -> OrgUnitTypeResponse
                        .builder().displayName(level.getDisplayName()).value(level.name()).hasParent(level.isParentLevel()).build())
                .toList();
    }

    public static List<RolesResponse> mapRoles() {
        return Arrays.stream(Roles.values()).map(roles -> new RolesResponse(
                roles.name(),
                roles.getDisplayName(),
                roles.getLevel()
        )).toList();
    }

    public static List<PermissionsResponse> mapPermissionsToDto() {
        return Arrays.stream(UserPermissions.values()).map(permission -> new PermissionsResponse(
                permission.name(),
                permission.getDisplayName()
        )).toList();
    }


    public User mapUserDtoToEntity(UserRequest request, OrgUnit orgUnit, User primaryManager, Set<User> secondaryManager) {
        return User.builder()
                .name(request.name())
                .password(encoder.encode(request.Password()))
                .email(request.Email())
                .permissions(request.permissions())
                .role(request.role())
                .orgUnit(orgUnit)
                .reportingManager(primaryManager)
                .secondaryManagers(secondaryManager)
                .build();

    }


}
