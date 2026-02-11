package Com.Crm.Travel.common.enums.Response;

public record EnumResponse(String name,
                           String displayName) {
    public record UserResponse(
            Long id,
            String name

    ) {

    }
}


