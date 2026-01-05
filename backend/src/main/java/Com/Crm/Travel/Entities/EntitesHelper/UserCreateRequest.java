package Com.Crm.Travel.Entities.EntitesHelper;

import java.util.Set;

import Com.Crm.Travel.common.enums.AppUserPermissions;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
                @NotBlank String name,
                @Email String Email,
                @NotBlank String Password,
                String Department,
                Set<AppUserPermissions> permissions) {
}
