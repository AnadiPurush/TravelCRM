package Com.Crm.Travel.Services.quarieServices;

import org.springframework.data.domain.Page;

import Com.Crm.Travel.Entities.Quaries;

public interface QuariesServices {

        // Save
        void saveQuaries(Quaries quaries);

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

        Page<Quaries> findAllWithFilters(Quaries filter,
                        int page,
                        int size,
                        String sortBy);

}
