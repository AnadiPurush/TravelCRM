package Com.Crm.Travel.Entities.EntitesHelper;

import Com.Crm.Travel.common.enums.QueriesPriority;
import Com.Crm.Travel.common.enums.QueriesStatus;

import java.util.Date;
import java.util.List;

public record QuariesHelper(String requesterName,
                            String contactNo,
                            String email,
                            List<String> Destination,
                            String fromLocation,
                            Date fromDate,
                            Date toDate,
                            Long quotedPrice,
                            List<String> requiredServices,
                            QueriesStatus queriesStatus,
                            QueriesPriority queriesPriority,
                            String assignedUseremail) { }
