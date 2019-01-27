package br.com.temwifi.domains.locations.service;

import br.com.temwifi.domains.locations.entity.interfaces.LocationEntity;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import br.com.temwifi.domains.locations.model.request.PostLocationRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.date.LocalDateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

public class CreateLocationService implements Service<PostLocationRequest, LocationDTO> {

    private static final Logger LOGGER = LogManager.getLogger(CreateLocationService.class);

    private LocationEntity locationEntity;

    @Inject
    CreateLocationService(LocationEntity locationEntity) { this.locationEntity = locationEntity; }

    /**
     * Register a new location
     *
     * @param request   service input
     * @return          new location dto
     * @throws Exception
     */
    @Override
    public LocationDTO execute(PostLocationRequest request) {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(UUID.randomUUID().toString());
        locationDTO.setCompleteAddress(request.getCompleteAddress());
        locationDTO.setStreet(request.getStreet());
        locationDTO.setNumber(request.getNumber());
        locationDTO.setComplement(request.getComplement());
        locationDTO.setPostCode(request.getPostCode());
        locationDTO.setCity(request.getCity());
        locationDTO.setState(request.getState());
        locationDTO.setCountry(Objects.isNull(request.getCountry()) ? "Brasil" : request.getCountry());
        locationDTO.setInsertDateTime(LocalDateTimeUtils.now());

        LOGGER.info(String.format("Registrando novo local: \n%s", MapperUtils.toJson(locationDTO)));
        locationEntity.createLocation(locationDTO);
        LOGGER.info("Local registrado com sucesso");

        return locationDTO;
    }
}
