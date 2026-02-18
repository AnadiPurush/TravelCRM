package com.crm.travel.user.domain;

import com.crm.travel.query.domain.QueryAssignment;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    @Column(nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private boolean superAdmin;
    private boolean manager;


    // life cycle control
    private boolean enabled = true;
    private boolean locked = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permissions", length = 1000)
    private Set<UserPermissions> permissions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<QueryAssignment> assignedQueries;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Enumerated(EnumType.STRING)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (this.superAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
            for (UserPermissions p : UserPermissions.values())
                authorities.add(new SimpleGrantedAuthority(p.name()));
        } else {
            for (UserPermissions p : this.permissions)
                authorities.add(new SimpleGrantedAuthority(p.name()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}


