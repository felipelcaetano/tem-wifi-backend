package br.com.temwifi.domains.rating.service;

import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.model.request.GetUserRequest;
import br.com.temwifi.domains.auth.service.ReadUserService;
import br.com.temwifi.domains.infra.model.response.Hypermedia;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.locations.component.DaggerLocationComponent;
import br.com.temwifi.domains.locations.model.request.GetLocationRequest;
import br.com.temwifi.domains.locations.model.request.PostLocationRatingRequest;
import br.com.temwifi.domains.locations.service.CreateLocationRatingService;
import br.com.temwifi.domains.locations.service.ReadLocationService;
import br.com.temwifi.domains.rating.component.DaggerRatingComponent;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import br.com.temwifi.domains.rating.model.request.PostInternetRatingRequest;
import br.com.temwifi.domains.rating.model.request.PostRatingRequest;
import br.com.temwifi.domains.rating.model.response.PostRatingResponse;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;

public class CreateRatingOrchestration implements Service<PostRatingRequest, PostRatingResponse> {

    private static final Logger LOGGER = LogManager.getLogger(CreateRatingOrchestration.class);

    private ReadLocationService readLocationService;
    private ReadUserService readUserService;
    private CreateRatingService createLocationService;
    private CreateLocationRatingService createLocationRatingService;

    @Inject
    CreateRatingOrchestration() {
        this.readLocationService = DaggerLocationComponent.create().buildReadLocationService();
        this.readUserService = DaggerAuthComponent.create().buildReadUserService();
        this.createLocationService = DaggerRatingComponent.create().buildCreateRatingService();
        this.createLocationRatingService = DaggerLocationComponent.create().buildCreateLocationRatingService();
    }

    /**
     * Orchestrate the calls in order to register a new rating
     *
     * @param request    service input
     * @return           rating id
     * @throws Exception
     */
    @Override
    public PostRatingResponse execute(PostRatingRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        validateRequest(request);

        GetLocationRequest getLocationRequest = new GetLocationRequest();
        getLocationRequest.setId(request.getLocationId());

        LOGGER.info(String.format("Pesquisando local pelo id: %s", request.getLocationId()));
        readLocationService.execute(getLocationRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setId(request.getUserId());

        LOGGER.info(String.format("Pesquisando usuário pelo id: %s", request.getUserId()));
        readUserService.execute(getUserRequest);

        LOGGER.info("Registrando avaliação");
        RatingDTO rating = createLocationService.execute(request);
        LOGGER.info(String.format("Avaliação %s registrada com sucesso", rating.getId()));

        LOGGER.info("Registrando avaliação no local");
        PostLocationRatingRequest postLocationRatingRequest = new PostLocationRatingRequest();
        postLocationRatingRequest.setLocationId(request.getLocationId());
        postLocationRatingRequest.setRatingId(rating.getId());
        createLocationRatingService.execute(postLocationRatingRequest);
        LOGGER.info("Avaliação registrada com sucesso para o local");

        Hypermedia hypermedia = new Hypermedia();
        hypermedia.setRel(rating.getId());
        hypermedia.setHref("rating/".concat(rating.getId()));

        PostRatingResponse postRatingResponse = new PostRatingResponse();
        postRatingResponse.setLinks(Arrays.asList(hypermedia));
        postRatingResponse.setStatusCode(HttpStatus.SC_CREATED);

        return postRatingResponse;
    }

    /**
     * Validate request
     *
     * @param request
     * @throws BadRequestException
     */
    private void validateRequest(PostRatingRequest request) throws BadRequestException {

        StringBuilder sb = new StringBuilder();
        LOGGER.info(String.format("Validando request: \n%s", MapperUtils.toJson(request)));
        if (Objects.isNull(request)) {
            LOGGER.error("Request inválido");
            sb.append("Request inválido");
            throw new BadRequestException(sb.toString());
        }

        LOGGER.info(String.format("Validando locationId %s", request.getLocationId()));
        if (Objects.isNull(request.getLocationId())) {
            LOGGER.error("Location Id inválido");
            sb.append("Location Id inválido. É obrigatório, ");
        }

        LOGGER.info(String.format("Validando userId %s", request.getUserId()));
        if (Objects.isNull(request.getUserId())) {
            LOGGER.error("User Id inválido");
            sb.append("User Id inválido. É obrigatório, ");
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

            PostInternetRatingRequest internet = request.getInternet();

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
