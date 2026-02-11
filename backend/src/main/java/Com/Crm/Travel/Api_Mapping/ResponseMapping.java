package Com.Crm.Travel.Api_Mapping;

import Com.Crm.Travel.Services.AppUserServices;
import Com.Crm.Travel.common.enums.Department;
import Com.Crm.Travel.common.enums.QueriesPriority;
import Com.Crm.Travel.common.enums.QueriesStatus;
import Com.Crm.Travel.common.enums.Response.EnumResponse;
import Com.Crm.Travel.common.enums.Response.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/response")
public class ResponseMapping {
    private final AppUserServices appUserServices;

    public ResponseMapping(AppUserServices appUserServices) {
        this.appUserServices = appUserServices;
    }

    @GetMapping("query/generate/response")
    public Map<String, Object> generateResponse() {
        List<EnumResponse.UserResponse> users = appUserServices.findByDepartment(Department.SALES).stream().map(user -> new EnumResponse.UserResponse(user.getId(), user.getName())).toList();
        return Map.of("Sales Person", users,
                "priorities", Mapper.map(QueriesPriority.values()),
                "status", Mapper.map(QueriesStatus.values())
        );
    }
}
