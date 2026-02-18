package com.crm.travel.user.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.user.dto.response.OrgUnitResponse;
import com.crm.travel.user.mapper.UserResponseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @file UserResponseController.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
@RestController
@ApiController
public class UserResponseController {
    @GetMapping("hierarchy/organization/levels")
    public List<OrgUnitResponse> getLevels() {
        return UserResponseMapper.mapOrgUnitsTypes();
    }

}
