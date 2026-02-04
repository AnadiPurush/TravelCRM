package Com.Crm.Travel.Services.servicesImpl.quariesImpl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Com.Crm.Travel.Entities.Quaries;
import Com.Crm.Travel.Repo.QuariesRepo;
import Com.Crm.Travel.Services.quarieServices.QuariesServices;

@Service
public class QuariesServicesImpl implements QuariesServices {

    private final QuariesRepo quariesRepo;

    public QuariesServicesImpl(QuariesRepo quariesRepo) {
        this.quariesRepo = quariesRepo;
    }

    @SuppressWarnings("null")
    @Override
    public void saveQuaries(Quaries quaries) {
        quariesRepo.save(quaries);

    }

    @Override
    public Page<Quaries> findByAppUser_id(Long Id, int page, int size, String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAppUser_id(Id, pageable1);
    }

    @Override
    public Page<Quaries> getQueriesByUserWithFilters(Long userId, String status, int page, int size, String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAppUser_idAndQuariesStatus(userId, status, pageable1);

    }

    @Override
    public Page<Quaries> findByAppUser_idAndQuariesPriority(Long userId, String priority, int page, int size,
            String sortBy) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return quariesRepo.findByAppUser_AndQuariesPriority(userId, priority, pageable1);

    }

    @Override
    public Page<Quaries> findAllWithFilters(Quaries filter, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Quaries> example = Example.of(filter, matcher);

        return quariesRepo.findAll(example, pageable);
    }
}
