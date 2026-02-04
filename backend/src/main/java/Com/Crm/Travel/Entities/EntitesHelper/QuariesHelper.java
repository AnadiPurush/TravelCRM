package Com.Crm.Travel.Entities.EntitesHelper;

import java.util.ArrayList;
import java.util.Date;

import Com.Crm.Travel.common.enums.QuariesPriority;
import Com.Crm.Travel.common.enums.QuariesStatus;

public record QuariesHelper(String requesterName,
        String contactNo,
        String email,
        ArrayList<String> Destination,
        String fromLocation,
        Date fromDate,
        Date toDate,
        Long quotedPrice,
        ArrayList<String> requiredServices,
        QuariesStatus quariesStatus,
        QuariesPriority quariesPriority,
        String assignedUseremail) { }
