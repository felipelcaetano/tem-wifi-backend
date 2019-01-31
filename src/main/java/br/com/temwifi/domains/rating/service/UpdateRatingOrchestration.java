package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.GetRatingRequest;
import br.com.temwifi.domains.rating.model.request.PutInternetRatingRequest;
import br.com.temwifi.domains.rating.model.request.PutRatingRequest;
import br.com.temwifi.domains.rating.model.response.PutRatingResponse;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;

public class UpdateRatingOrchestration implements Service<PutRatingRequest, PutRatingResponse> {

    private static final Logger LOGGER = LogManager.getLogger(UpdateRatingOrchestration.class);

    private ReadRatingService readRatingService;
    private UpdateRatingService updateRatingService;

    @Inject
    UpdateRatingOrchestration() {
        this.readRatingService = DaggerRatingComponent.create().buildReadRatingService();
        this.updateRatingService = DaggerRatingComponent.create().buildUpdateRatingService();
    }

    /**
     * Orchestrate the calls in order to register a new rating
     *
     * @param request    service input
     * @return           rating id
     * @throws Exception
     */
    @Override
    public PutRatingResponse execute(PutRatingRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        validateRequest(request);

        GetRatingRequest getRatingRequest = new GetRatingRequest();
        getRatingRequest.setId(request.getId());
        LOGGER.info(String.format("Pesquisando avaliação pelo id: %s", request.getId()));
        readRatingService.execute(getRatingRequest);

        LOGGER.info("Atualizando avaliação");
        RatingDTO rating = updateRatingService.execute(request);
        LOGGER.info(String.format("Avaliação %s atualizada com sucesso", rating.getId()));

        PutRatingResponse putRatingResponse = new PutRatingResponse();
        putRatingResponse.setStatusCode(HttpStatus.SC_NO_CONTENT);

        return putRatingResponse;
    }

    /**
     * Validate request
     *
     * @param request
     * @throws BadRequestException
     */
    private void validateRequest(PutRatingRequest request) throws BadRequestException {

        StringBuilder sb = new StringBuilder();
        LOGGER.info(String.format("Validando request: \n%s", MapperUtils.toJson(request)));
        if (Objects.isNull(request)) {
            LOGGER.error("Request inválido");
            sb.append("Request inválido");
            throw new BadRequestException(sb.toString());
        }

        LOGGER.info(String.format("Validando id %s", request.getId()));
        if (Objects.isNull(request.getId())) {
            LOGGER.error("Id inválido");
            sb.append("Id inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando atendimento %s", request.getTreatment()));
        if (Objects.isNull(request.getTreatment())) {
            LOGGER.error("Atendimento inválido");
            sb.append("Atendimento inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando preço %s", request.getPrice()));
        if (Objects.isNull(request.getPrice())) {
            LOGGER.error("Preço inválido");
            sb.append("Preço inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando conforto %s", request.getComfort()));
        if (Objects.isNull(request.getComfort())) {
            LOGGER.error("Conforto inválido");
            sb.append("Conforto inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando ruído %s", request.getNoise()));
        if (Objects.isNull(request.getNoise())) {
            LOGGER.error("Ruído inválido");
            sb.append("Ruído inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando avaliação geral %s", request.getGeneralRating()));
        if (Objects.isNull(request.getGeneralRating())) {
            LOGGER.error("Avaliação Geral inválida");
            sb.append("Avaliação Geral inválida. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando internet %s", MapperUtils.toJson(request.getInternet())));
        if (Objects.isNull(request.getInternet())) {
            LOGGER.error("Internet inválida");
            sb.append("Internet inválida. É obrigatório");
        } else {

            PutInternetRatingRequest internet = request.getInternet();

            LOGGER.info(String.format("Validando se tem internet %s", internet.getHasInternet()));
            if(Objects.isNull(internet.getHasInternet())) {
                LOGGER.error("Tem Internet inválido");
                sb.append("Tem Internet inválido. É obrigatório, ");
            }

            LOGGER.info(String.format("Validando se a internet é aberta %s", internet.getIsOpened()));
            if(Objects.isNull(internet.getIsOpened())) {
                LOGGER.error("Internet Aberta inválida");
                sb.append("Internet Aberta inválida. É obrigatório, ");
            }

            LOGGER.info(String.format("Validando velocidade da internet %s", internet.getSpeed()));
            if(Objects.isNull(internet.getSpeed())) {
                LOGGER.error("Velocidade da Internet Internet inválida");
                sb.append("Velocidade da Internet inválida. É obrigatório, ");
            }

            if(Boolean.FALSE.equals(internet.getIsOpened())) {
                LOGGER.info(String.format("Validando senha da internet %s", internet.getPass()));

                if(Objects.isNull(internet.getPass())) {
                    LOGGER.error("Senha da Internet Internet inválida");
                    sb.append("Senha da Internet inválida. É obrigatório, ");
                }
            }
        }

        if (sb.length() > 0)
            throw new BadRequestException(sb.toString());
    }
}
