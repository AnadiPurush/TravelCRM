package com.crm.travel.user.dto.request;

import java.util.UUID;

/**
 * @file OrgUnitCreateReq.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
public record OrgUnitCreateReq(String name,
//                               OrgUnitType type,
                               UUID parentId) {
}
