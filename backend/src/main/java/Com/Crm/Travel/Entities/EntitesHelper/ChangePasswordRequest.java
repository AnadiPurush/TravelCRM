package Com.Crm.Travel.Entities.EntitesHelper;

import jakarta.annotation.Nullable;

public record ChangePasswordRequest(
                @Nullable String oldPassword,
                String newPassword

) {
}
