package br.com.temwifi.domains.locations.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.locations.component.DaggerLocationComponent;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import br.com.temwifi.domains.locations.model.request.GetLocationRequest;
import br.com.temwifi.domains.locations.model.response.GetLocationResponse;
import br.com.temwifi.domains.locations.service.ReadLocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Controller
public class GetLocation implements AwsApiRestHandler<Void, GetLocationResponse> {

    private static final Logger LOGGER = LogManager.getLogger(GetLocation.class);

    private static final String LOCATION_ID = "locationId";

    private ReadLocationService readLocationService;

    public GetLocation() {
        readLocationService = DaggerLocationComponent.create().buildReadLocationService();
    }

    /**
     * Find a location by it's id
     *
     * @param body
     * @param httpContext
     * @return              location's data
     * @throws HttpException
     */
    @Override
    public GetLocationResponse handleRequest(Void body, AwsHttpContext httpContext) throws HttpException {

        LOGGER.info("Obtendo path parameters");
        if(Objects.isNull(httpContext) || Objects.isNull(httpContext.getPathParameters()) ||
                httpContext.getPathParameters().isEmpty() || !httpContext.getPathParameters().containsKey(LOCATION_ID)) {
            LOGGER.error("Location Id não informado");
            throw new BadRequestException("Location Id não informado");
        }

        GetLocationRequest getLocationRequest = new GetLocationRequest();
        getLocationRequest.setId(httpContext.getPathParameters().get(LOCATION_ID));
        LocationDTO location = readLocationService.execute(getLocationRequest);

        GetLocationResponse getLocationResponse = new GetLocationResponse();
        getLocationResponse.setType(location.getType());
        getLocationResponse.setName(location.getName());
        getLocationResponse.setCity(location.getCity());
        getLocationResponse.setComplement(location.getComplement());
        getLocationResponse.setCountry(location.getCountry());
        getLocationResponse.setId(location.getId());
        getLocationResponse.setNumber(location.getNumber());
        getLocationResponse.setPostCode(location.getPostCode());
        getLocationResponse.setRatings(location.getRatings());
        getLocationResponse.setState(location.getState());
        getLocationResponse.setStreet(location.getStreet());
        getLocationResponse.setRatingsCount(Objects.isNull(location.getRatings()) ? 0 : location.getRatings().size());

        return getLocationResponse;
    }
}
