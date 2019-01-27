package br.com.temwifi.domains.rating.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.GetRatingRequest;
import br.com.temwifi.domains.rating.model.response.GetInrenetRatingResponse;
import br.com.temwifi.domains.rating.model.response.GetRatingResponse;
import br.com.temwifi.domains.rating.service.ReadRatingService;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

@Controller
public class GetRating implements AwsApiRestHandler<Void, GetRatingResponse> {

    private static final Logger LOGGER = LogManager.getLogger(GetRating.class);

    private static final String RATING_ID = "ratingId";

    private ReadRatingService readRatingService;

    public GetRating() {
        readRatingService = DaggerRatingComponent.create().buildReadRatingService();
    }

    /**
     * Get a specifc rating and it's data
     *
     * @param body
     * @param httpContext
     * @return              rating's data
     * @throws HttpException
     */
    @Override
    public GetRatingResponse handleRequest(Void body, AwsHttpContext httpContext) throws HttpException {

        Map<String, String> pathParameters = httpContext.getPathParameters();

        LOGGER.info("Obtendo path parameters");

        if(Objects.isNull(pathParameters) || pathParameters.isEmpty() || !pathParameters.containsKey(RATING_ID)) {
            LOGGER.error("Id da avaliação não informado");
            throw new BadRequestException("Id da avaliação não informado");
        }

        GetRatingRequest getRatingRequest = new GetRatingRequest();
        getRatingRequest.setId(pathParameters.get(RATING_ID));

        RatingDTO rating = readRatingService.execute(getRatingRequest);

        GetRatingResponse getRatingResponse = new GetRatingResponse();
        getRatingResponse.setComfort(rating.getComfort());
        getRatingResponse.setDrinks(rating.getDrinks());
        getRatingResponse.setFoods(rating.getFoods());
        getRatingResponse.setGeneralRating(rating.getGeneralRating());
        getRatingResponse.setId(rating.getId());
        getRatingResponse.setLocationId(rating.getLocationId());
        getRatingResponse.setNoise(rating.getNoise());
        getRatingResponse.setPrice(rating.getPrice());
        getRatingResponse.setTreatment(rating.getTreatment());
        getRatingResponse.setUserId(rating.getUserId());

        if(!Objects.isNull(rating.getInternet())) {
            GetInrenetRatingResponse getInrenetRatingResponse = new GetInrenetRatingResponse();
            getInrenetRatingResponse.setHasInternet(rating.getInternet().getHasInternet());
            getInrenetRatingResponse.setOpened(rating.getInternet().getOpened());
            getInrenetRatingResponse.setPass(rating.getInternet().getPass());
            getInrenetRatingResponse.setSpeed(rating.getInternet().getSpeed());

            getRatingResponse.setInternet(getInrenetRatingResponse);
        }

        return getRatingResponse;
    }
}
