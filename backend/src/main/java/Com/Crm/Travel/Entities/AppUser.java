package Com.Crm.Travel.Entities;

import Com.Crm.Travel.common.enums.AppUserPermissions;
import Com.Crm.Travel.common.enums.Department;
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

public class AppUser implements UserDetails {
    @Id
    @GeneratedValue
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
    @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permissions", length = 1000)

    private Set<AppUserPermissions> permissions = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    private List<Quaries> assignedQuaries;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (this.superAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
            for (AppUserPermissions p : AppUserPermissions.values())
                authorities.add(new SimpleGrantedAuthority(p.name()));
        } else {
            for (AppUserPermissions p : this.permissions)
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
        return "AppUser{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}


