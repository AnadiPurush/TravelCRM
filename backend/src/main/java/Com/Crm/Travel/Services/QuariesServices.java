package Com.Crm.Travel.Services;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.DTO.QuariesDetailDTO;
import Com.Crm.Travel.Entities.EntitesHelper.QuariesHelper;
import Com.Crm.Travel.Entities.Quaries;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface QuariesServices {

        // Save
        boolean saveQuaries(QuariesHelper helper, Authentication authentication);

        // Find by assigned user ID with pagination
        public Page<Quaries> findByAppUser_id(Long id, int page, int size, String sortBy);

        // Find by assigned user ID and status with pagination
        public Page<Quaries> getQueriesByUserWithFilters(
                        Long id,
                        String status,
                        int page,
                        int size,
                        String sortBy);

        // Find by assigned user ID and priority with pagination
        Page<Quaries> findByAppUser_idAndQuariesPriority(
                        Long id,
                        String priority,
                        int page,
                        int size,
                        String sortBy);

        // Find by assigned user ID and filters

        Page<QuariesDetailDTO> findAllWithFilters(QuariesHelper helper,
                                                  AppUser user,
                                                  int page,
                                                  int size,
                                                  String sortBy);

        Quaries findById(Long Id);
}
