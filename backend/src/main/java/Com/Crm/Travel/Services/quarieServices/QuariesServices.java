package Com.Crm.Travel.Services.quarieServices;

import java.util.Optional;

import Com.Crm.Travel.Entities.Quaries;

public interface QuariesServices {
    Optional<Quaries> saveQuaries(Quaries quaries);
}
