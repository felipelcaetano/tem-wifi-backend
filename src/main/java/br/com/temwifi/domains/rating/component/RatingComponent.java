package br.com.temwifi.domains.rating.component;

import br.com.temwifi.domains.rating.module.RatingModule;
import br.com.temwifi.domains.rating.service.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = RatingModule.class)
public interface RatingComponent {

    CreateRatingService buildCreateRatingService();

    ReadRatingService buildReadRatingService();

    ReadRatingsService buildReadRatingsService();

    CreateRatingOrchestration buildCreateRatingOrchestration();

    UpdateRatingService buildUpdateRatingService();

    UpdateRatingOrchestration buildUpdateRatingOrchestration();
}
