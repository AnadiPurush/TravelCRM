package Com.Crm.Travel.Entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Com.Crm.Travel.common.enums.AppUserPermissions;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private boolean superAdmin;
    private String department;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permissions")
    private Set<AppUserPermissions> permissions = new HashSet<>();

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

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
