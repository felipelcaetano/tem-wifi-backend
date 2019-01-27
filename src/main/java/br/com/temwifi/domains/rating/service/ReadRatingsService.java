package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.GetRatingsRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

public class ReadRatingsService implements Service<GetRatingsRequest, List<RatingDTO>> {

    private static final Logger LOGGER = LogManager.getLogger(ReadRatingsService.class);

    private RatingEntity ratingEntity;

    @Inject
    public ReadRatingsService(RatingEntity ratingEntity) {
        this.ratingEntity = ratingEntity;
    }

    /**
     * Read a rating by it's id
     *
     * @param request       service input
     * @return              rating dto
     * @throws Exception
     */
    @Override
    public List<RatingDTO> execute(GetRatingsRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        validate(request);

        LOGGER.info(String.format("Pesquisando ratings de %s", !Objects.isNull(request.getLocationId()) ?
                "local ".concat(request.getLocationId()) : "usuário ".concat(request.getUserId())));

        if(!Objects.isNull(request.getLocationId())) {
            return ratingEntity.readRatingsByLocationId(request.getLocationId());

        } else {
            return ratingEntity.readRatingsByUserId(request.getUserId());
        }
    }

    /**
     * Validate request
     *
     * @param request
     * @throws HttpException
     */
    private void validate(GetRatingsRequest request) throws HttpException {

        LOGGER.info(String.format("Validando request: \n%s", MapperUtils.toJson(request)));

        if(Objects.isNull(request)) {
            LOGGER.error("Request inválido");
            throw new BadRequestException("Request inválido");
        }

        if(Objects.isNull(request.getLocationId()) && Objects.isNull(request.getUserId())) {
            LOGGER.error("Request inválido");
            throw new BadRequestException("Request inválido. Informe um dos filtros");
        }
    }
}
