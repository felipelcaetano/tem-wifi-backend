package br.com.temwifi.domains.rating.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.request.PostRatingRequest;
import br.com.temwifi.domains.rating.model.response.PostRatingResponse;
import br.com.temwifi.domains.rating.service.CreateRatingOrchestration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class PostRating implements AwsApiRestHandler<PostRatingRequest, PostRatingResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PostRating.class);

    private CreateRatingOrchestration createRatingOrchestration;

    public PostRating() {
        createRatingOrchestration = DaggerRatingComponent.create().buildCreateRatingOrchestration();
    }

    /**
     * Register a new rating
     *
     * @param body
     * @param httpContext
     * @return              new rating id
     * @throws HttpException
     */
    @Override
    public PostRatingResponse handleRequest(PostRatingRequest body, AwsHttpContext httpContext) throws HttpException {

        return createRatingOrchestration.execute(body);
    }
}
