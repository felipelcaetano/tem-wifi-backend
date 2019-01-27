package br.com.temwifi.domains.locations.service;

import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.locations.entity.interfaces.LocationEntity;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import br.com.temwifi.domains.locations.model.request.PostLocationRatingRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.date.LocalDateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

public class CreateLocationRatingService implements Service<PostLocationRatingRequest, LocationDTO> {

    private static final Logger LOGGER = LogManager.getLogger(CreateLocationRatingService.class);

    private LocationEntity locationEntity;

    @Inject
    CreateLocationRatingService(LocationEntity locationEntity) { this.locationEntity = locationEntity; }

    /**
     * Register a new rating for a location
     *
     * @param request   service input
     * @return          location updated dto
     * @throws Exception
     */
    @Override
    public LocationDTO execute(PostLocationRatingRequest request) throws BadRequestException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        validate(request);

        LOGGER.info(String.format("Consultando local %s", request.getLocationId()));
        Optional<LocationDTO> location = locationEntity.readLocationById(request.getLocationId());

        if(!location.isPresent()) {
            LOGGER.error("Local não encontrado");
            throw new BadRequestException(String.format("Local %s não encontrado", request.getLocationId()));
        }

        LocationDTO locationDTO = location.get();

        if(locationDTO.getRatings().isEmpty()) {
            locationDTO.setRatings(new HashSet());
        }

        locationDTO.getRatings().add(request.getRatingId());
        locationDTO.setUpdateDateTime(LocalDateTimeUtils.now());

        LOGGER.info(String.format("Registrando avaliação para o local: \n%s", MapperUtils.toJson(locationDTO)));
        locationEntity.createLocation(locationDTO);
        LOGGER.info("Local atualizado com sucesso");

        return locationDTO;
    }

    /**
     * Validate request
     *
     * @param request
     * @throws BadRequestException
     */
    public void validate(PostLocationRatingRequest request) throws BadRequestException {

        LOGGER.info("Validando request");

        if(Objects.isNull(request)) {
            LOGGER.error("Request inválido");
            throw new BadRequestException("Request inválido");
        }

        if(Objects.isNull(request.getLocationId()) || Objects.isNull(request.getRatingId())) {
            LOGGER.error("Request inválido");
            throw new BadRequestException("Request inválido. Parâmetros não informados");
        }
    }
}
