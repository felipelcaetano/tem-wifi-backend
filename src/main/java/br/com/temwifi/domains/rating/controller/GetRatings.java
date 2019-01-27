package br.com.temwifi.domains.rating.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.GetRatingsRequest;
import br.com.temwifi.domains.rating.model.response.GetInrenetRatingResponse;
import br.com.temwifi.domains.rating.model.response.GetRatingResponse;
import br.com.temwifi.domains.rating.model.response.GetRatingsResponse;
import br.com.temwifi.domains.rating.service.ReadRatingsService;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class GetRatings implements AwsApiRestHandler<Void, GetRatingsResponse> {

    private static final Logger LOGGER = LogManager.getLogger(GetRatings.class);

    private static final String LOCATION_ID = "locationId";
    private static final String USER_ID = "userId";

    private ReadRatingsService readRatingsService;

    public GetRatings() {
        readRatingsService = DaggerRatingComponent.create().buildReadRatingsService();
    }

    /**
     * Get list of ratings from a user or location
     *
     * @param body
     * @param httpContext
     * @return              list of ratings and their data
     * @throws HttpException
     */
    @Override
    public GetRatingsResponse handleRequest(Void body, AwsHttpContext httpContext) throws HttpException {

        Map<String, String> queryStringParameters = httpContext.getQueryStringParameters();

        LOGGER.info("Obtendo query parameters");
        if(queryStringParameters.isEmpty()) {
            LOGGER.error("Parâmetros não informados");
            throw new BadRequestException("Parâmetros não informados");
        }

        if(!queryStringParameters.containsKey(LOCATION_ID) && !queryStringParameters.containsKey(USER_ID)) {
            LOGGER.error("Parâmetros não informados");
            throw new BadRequestException("Parâmetros não informados");
        }

        GetRatingsRequest getRatingsRequest = new GetRatingsRequest();
        if(queryStringParameters.containsKey(LOCATION_ID))
            getRatingsRequest.setLocationId(queryStringParameters.get(LOCATION_ID));

        if(queryStringParameters.containsKey(USER_ID))
            getRatingsRequest.setUserId(queryStringParameters.get(USER_ID));

        List<RatingDTO> ratings = readRatingsService.execute(getRatingsRequest);

        GetRatingsResponse getRatingsResponse = new GetRatingsResponse();

        List<GetRatingResponse> returnRatings = ratings
                .stream()
                .map(rating -> {
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

                    if (!Objects.isNull(rating.getInternet())) {
                        GetInrenetRatingResponse getInrenetRatingResponse = new GetInrenetRatingResponse();
                        getInrenetRatingResponse.setHasInternet(rating.getInternet().getHasInternet());
                        getInrenetRatingResponse.setOpened(rating.getInternet().getOpened());
                        getInrenetRatingResponse.setPass(rating.getInternet().getPass());
                        getInrenetRatingResponse.setSpeed(rating.getInternet().getSpeed());

                        getRatingResponse.setInternet(getInrenetRatingResponse);
                    }

                    return getRatingResponse;
                })
                .collect(Collectors.toList());

        getRatingsResponse.setRatings(returnRatings);

        LOGGER.info(String.format("Retornado : \n%s", MapperUtils.toJson(getRatingsResponse)));

        return getRatingsResponse;
    }
}
