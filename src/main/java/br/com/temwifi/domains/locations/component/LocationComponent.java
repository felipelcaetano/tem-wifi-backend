package br.com.temwifi.domains.locations.component;

import br.com.temwifi.domains.locations.module.LocationModule;
import br.com.temwifi.domains.locations.service.CreateLocationOrchestration;
import br.com.temwifi.domains.locations.service.CreateLocationRatingService;
import br.com.temwifi.domains.locations.service.CreateLocationService;
import br.com.temwifi.domains.locations.service.ReadLocationService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = LocationModule.class)
public interface LocationComponent {

    ReadLocationService buildReadLocationService();

    CreateLocationService buildCreateLocationService();

    CreateLocationOrchestration buildCreateLocationOrchestration();

    CreateLocationRatingService buildCreateLocationRatingService();

}
