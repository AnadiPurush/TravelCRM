package com.crm.travel.user.specification;

import com.crm.travel.user.domain.User;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.UUID;

/**
 * @file UserSpecifications.java
 * Author: Utsav
 * Date: 2/26/26
 *
 */
public class UserSpecifications {
    //    Recursive call to self on org unit to retrieve the children is as per the current logged-in user's


    public static Specification<User> inOrgUnits(Set<UUID> orgUnitsIds) {
        return ((root, query, criteriaBuilder) -> {
            if (orgUnitsIds == null || orgUnitsIds.isEmpty()) return criteriaBuilder.disjunction();
            root.fetch("orgUnits", JoinType.INNER);
            return root.get("orgUnits").get("id").in(orgUnitsIds);
        });
    }

    public static Specification<User> keyWord(String keyword) {

        return (((root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) return criteriaBuilder.conjunction();
            else {
                String pattern = "%" + keyword.toLowerCase() + "%";

                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), pattern),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("id")), pattern)
                );
            }
        }
        ));
    }


}
