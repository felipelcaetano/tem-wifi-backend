package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import br.com.temwifi.domains.rating.model.dto.InternetRatingDTO;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.PostRatingRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.date.LocalDateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class CreateRatingService implements Service<PostRatingRequest, RatingDTO> {

    private static final Logger LOGGER = LogManager.getLogger(CreateRatingService.class);

    RatingEntity ratingEntity;

    @Inject
    public CreateRatingService(RatingEntity ratingEntity) {
        this.ratingEntity = ratingEntity;
    }

    /**
     * Register a new rating for a location from an user
     *
     * @param request       service input
     * @return              rating dto
     * @throws Exception
     */
    @Override
    public RatingDTO execute(PostRatingRequest request) {

        RatingDTO rating = new RatingDTO();
        InternetRatingDTO internet = new InternetRatingDTO();
        internet.setHasInternet(request.getInternet().getHasInternet());
        internet.setOpened(request.getInternet().getOpened());
        internet.setSpeed(request.getInternet().getSpeed());
        internet.setPass(request.getInternet().getPass());

        rating.setInternet(internet);

        rating.setId(UUID.randomUUID().toString());
        rating.setLocationId(request.getLocationId());
        rating.setUserId(request.getUserId());
        rating.setFoods(request.getFoods());
        rating.setDrinks(request.getDrinks());
        rating.setComfort(request.getComfort());
        rating.setTreatment(request.getTreatment());
        rating.setGeneralRating(request.getGeneralRating());
        rating.setNoise(request.getNoise());
        rating.setPrice(request.getPrice());
        rating.setInsertDateTime(LocalDateTimeUtils.now());

        LOGGER.info(String.format("Registrando avaliação : \n%s", MapperUtils.toJson(rating)));
        ratingEntity.createRating(rating);
        LOGGER.info(String.format("Avaliação %s registrada com sucesso", rating.getId()));

        return rating;
    }
}
