package br.com.temwifi.domains.locations.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.locations.component.DaggerLocationComponent;
import br.com.temwifi.domains.locations.model.request.PostLocationRequest;
import br.com.temwifi.domains.locations.model.response.PostLocationResponse;
import br.com.temwifi.domains.locations.service.CreateLocationOrchestration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class PostLocation implements AwsApiRestHandler<PostLocationRequest, PostLocationResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PostLocation.class);

    private CreateLocationOrchestration createLocationOrchestration;

    public PostLocation() {
        createLocationOrchestration = DaggerLocationComponent.create().buildCreateLocationOrchestration();
    }

    /**
     * Register a new location
     *
     * @param body
     * @param httpContext
     * @return              new location id
     * @throws HttpException
     */
    @Override
    public PostLocationResponse handleRequest(PostLocationRequest body, AwsHttpContext httpContext) throws HttpException {

        return createLocationOrchestration.execute(body);
    }
}
