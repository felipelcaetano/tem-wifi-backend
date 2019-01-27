package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.GetRatingRequest;
import br.com.temwifi.interfaces.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class ReadRatingService implements Service<GetRatingRequest, RatingDTO> {

    private static final Logger LOGGER = LogManager.getLogger(ReadRatingService.class);

    private RatingEntity ratingEntity;

    @Inject
    public ReadRatingService(RatingEntity ratingEntity) {
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
    public RatingDTO execute(GetRatingRequest request) throws HttpException {

        if(Objects.isNull(request))
            throw new BadRequestException("Request inválido");

        LOGGER.info(String.format("Pesquisando rating %s", request.getId()));
        Optional<RatingDTO> rating = ratingEntity.readRatingById(request.getId());

        if(!rating.isPresent()) {
            LOGGER.info("Rating não encontrado");
            throw new ResourceNotFoundException(String.format("Rating %s não encontrado", request.getId()));
        }

        return rating.get();
    }
}
