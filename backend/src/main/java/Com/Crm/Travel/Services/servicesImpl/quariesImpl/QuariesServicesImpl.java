package Com.Crm.Travel.Services.servicesImpl.quariesImpl;

import java.util.Optional;

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

    @Override
    public Optional<Quaries> saveQuaries(Quaries quaries) {
        quariesRepo.save(quaries);
        return Optional.empty();
    }

}
