package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import br.com.temwifi.domains.rating.model.dto.InternetRatingDTO;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.PutRatingRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.date.LocalDateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateRatingService implements Service<PutRatingRequest, RatingDTO> {

    private static final Logger LOGGER = LogManager.getLogger(UpdateRatingService.class);

    RatingEntity ratingEntity;

    @Inject
    public UpdateRatingService(RatingEntity ratingEntity) {
        this.ratingEntity = ratingEntity;
    }

    /**
     * Update an existing rating with new data
     *
     * @param request       service input
     * @return              rating dto
     * @throws Exception
     */
    @Override
    public RatingDTO execute(PutRatingRequest request) {

        RatingDTO rating = new RatingDTO();
        InternetRatingDTO internet = new InternetRatingDTO();
        internet.setHasInternet(request.getInternet().getHasInternet());
        internet.setOpened(request.getInternet().getIsOpened());
        internet.setSpeed(request.getInternet().getSpeed());
        internet.setPass(request.getInternet().getPass());

        rating.setInternet(internet);

        rating.setId(request.getId());
        rating.setFoods(request.getFoods());
        rating.setDrinks(request.getDrinks());
        rating.setComfort(request.getComfort());
        rating.setTreatment(request.getTreatment());
        rating.setGeneralRating(request.getGeneralRating());
        rating.setNoise(request.getNoise());
        rating.setPrice(request.getPrice());
        rating.setUpdateDateTime(LocalDateTimeUtils.now());

        LOGGER.info(String.format("Atualizando avaliação : \n%s", MapperUtils.toJson(rating)));
        ratingEntity.updateRating(rating);
        LOGGER.info(String.format("Avaliação %s atualizada com sucesso", rating.getId()));

        return rating;
    }
}
