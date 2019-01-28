package br.com.temwifi.domains.locations.service;

import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.domains.locations.entity.interfaces.LocationEntity;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import br.com.temwifi.domains.locations.model.request.GetLocationRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class ReadLocationService implements Service<GetLocationRequest, LocationDTO> {

    private static final Logger LOGGER = LogManager.getLogger(ReadLocationService.class);

    private LocationEntity locationEntity;

    @Inject
    public ReadLocationService(LocationEntity locationEntity) { this.locationEntity = locationEntity; }

    /**
     * Read a location by it's id or complete address
     *
     * @param request   service input
     * @return          new location dto
     * @throws Exception
     */
    @Override
    public LocationDTO execute(GetLocationRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        if(Objects.isNull(request))
            throw new BadRequestException("Request inválido");

        if(Objects.isNull(request.getId()) && Objects.isNull(request.getCompleteAddress()) && Objects.isNull(request.getName()))
            throw new BadRequestException("Request inválido. Informe algum dos filtros");

        Optional<LocationDTO> location = Optional.empty();

        if(!Objects.isNull(request.getId())) {
            LOGGER.info(String.format("Pesquisando local por id [%s]", request.getId()));
            location = locationEntity.readLocationById(request.getId());

        } else {

            if(!Objects.isNull(request.getCompleteAddress())) {
                LOGGER.info(String.format("Pesquisando local por endereço [%s]", request.getCompleteAddress()));
                location = locationEntity.readLocationByCompleteAddress(request.getCompleteAddress());
            }

            if(!location.isPresent() && !Objects.isNull(request.getName())) {
                LOGGER.info(String.format("Pesquisando local por nome [%s]", request.getName()));
                location = locationEntity.readLocationByCompleteAddress(request.getCompleteAddress());
            }
        }

        if(!location.isPresent()) {
            LOGGER.info("Local não encontrado");
            throw new ResourceNotFoundException(String.format("Local %s não encontrado", request.getId()));
        }

        return location.get();
    }
}
