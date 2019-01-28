package br.com.temwifi.domains.rating.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.request.PutRatingRequest;
import br.com.temwifi.domains.rating.model.response.PutRatingResponse;
import br.com.temwifi.domains.rating.service.UpdateRatingOrchestration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class PutRating implements AwsApiRestHandler<PutRatingRequest, PutRatingResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PutRating.class);

    private UpdateRatingOrchestration updateRatingOrchestration;

    public PutRating() {
        updateRatingOrchestration = DaggerRatingComponent.create().buildUpdateRatingOrchestration();
    }

    /**
     * Update an existing rating
     *
     * @param body
     * @param httpContext
     * @return              no data
     * @throws HttpException
     */
    @Override
    public PutRatingResponse handleRequest(PutRatingRequest body, AwsHttpContext httpContext) throws HttpException {

        return updateRatingOrchestration.execute(body);
    }
}
