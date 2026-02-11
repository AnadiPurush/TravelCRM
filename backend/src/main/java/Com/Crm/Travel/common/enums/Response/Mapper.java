package Com.Crm.Travel.common.enums.Response;

import java.util.Arrays;
import java.util.List;

public class Mapper {
    public static <T extends Enum<T> & DisplayEnum> List<EnumResponse> map(T[] enumConstants) {
        return Arrays.stream(enumConstants)
                .map(e -> new EnumResponse(e.name(), e.getDisplayName()))
                .toList();
    }
}
