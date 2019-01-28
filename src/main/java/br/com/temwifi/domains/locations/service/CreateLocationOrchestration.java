package br.com.temwifi.domains.locations.service;

import br.com.temwifi.domains.infra.model.response.Hypermedia;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.domains.locations.component.DaggerLocationComponent;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import br.com.temwifi.domains.locations.model.request.GetLocationRequest;
import br.com.temwifi.domains.locations.model.request.PostLocationRequest;
import br.com.temwifi.domains.locations.model.response.PostLocationResponse;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.FormatUtils;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.cep.ViaCepService;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;

public class CreateLocationOrchestration implements Service<PostLocationRequest, PostLocationResponse> {

    private static final Logger LOGGER = LogManager.getLogger(CreateLocationOrchestration.class);

    private static String COMMA = ",";

    private ReadLocationService readLocationService;
    private CreateLocationService createLocationService;

    @Inject
    CreateLocationOrchestration() {
        this.readLocationService = DaggerLocationComponent.create().buildReadLocationService();
        this.createLocationService = DaggerLocationComponent.create().buildCreateLocationService();
    }

    /**
     * Orchestrate the calls in order to register a new location
     *
     * @param request    service input
     * @return           location data
     * @throws Exception
     */
    @Override
    public PostLocationResponse execute(PostLocationRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        validateRequest(request);

        StringBuilder sb = new StringBuilder();
        sb.append(FormatUtils.removeAccent(request.getStreet().replaceAll("\\s", "").toUpperCase()));
        sb.append(COMMA);
        sb.append(FormatUtils.removeAccent(request.getNumber().replaceAll("\\s", "").toUpperCase()));
        sb.append(COMMA);

        if(!Objects.isNull(request.getComplement())) {
            sb.append(FormatUtils.removeAccent(request.getComplement().replaceAll("\\s", "").toUpperCase()));
            sb.append(COMMA);
        }

        sb.append(FormatUtils.removeAccent(request.getCity().trim().toUpperCase()));

        String completeAddress = sb.toString();

        request.setCompleteAddress(completeAddress);
        request.setNameIndex(FormatUtils.removeAccent(request.getName().replaceAll("\\s", "").toUpperCase()));

        GetLocationRequest getLocationRequest = new GetLocationRequest();
        getLocationRequest.setCompleteAddress(completeAddress);
        getLocationRequest.setName(request.getNameIndex());

        LOGGER.info(String.format("Pesquisando endereço: %s", completeAddress));
        try {
            readLocationService.execute(getLocationRequest);
        } catch (ResourceNotFoundException e) {
            LOGGER.info("Endereço não encontrado");
        }

        LocationDTO location = createLocationService.execute(request);

        Hypermedia hypermedia = new Hypermedia();
        hypermedia.setRel(location.getId());
        hypermedia.setHref("location/".concat(location.getId()));

        PostLocationResponse postLocationResponse = new PostLocationResponse();
        postLocationResponse.setLinks(Arrays.asList(hypermedia));
        postLocationResponse.setStatusCode(HttpStatus.SC_CREATED);

        return postLocationResponse;
    }

    /**
     * Validate request and post code
     *
     * @param request
     * @throws BadRequestException
     */
    private void validateRequest(PostLocationRequest request) throws BadRequestException {

        StringBuilder sb = new StringBuilder();

        LOGGER.info("Validando nome");
        if(Objects.isNull(request.getName())) {
            LOGGER.warn("Nome inválido");
            sb.append("Nome inválido. É obrigatório | ");
        }

        LOGGER.info("Validando logradouro");
        if(Objects.isNull(request.getStreet())) {
            LOGGER.warn("Logradouro inválido");
            sb.append("Logradouro inválido. É obrigatório | ");
        }

        LOGGER.info("Validando número");
        if(Objects.isNull(request.getNumber())) {
            LOGGER.warn("Número inválido");
            sb.append("Número inválido. É obrigatório | ");
        }

        LOGGER.info("Validando cep");
        if(Objects.isNull(request.getPostCode())) {
            LOGGER.warn("CEP inválido");
            sb.append("CEP inválido. É obrigatório | ");

        } else {

            try {
                new ViaCepService().execute(request.getPostCode());
            } catch (IllegalArgumentException e) {
                LOGGER.warn("CEP inválido - Via CEP");
                sb.append("CEP inválido. É obrigatório | ");
            }
        }

        LOGGER.info("Validando cidade");
        if(Objects.isNull(request.getCity())) {
            LOGGER.warn("Cidade inválida");
            sb.append("Cidade inválida. É obrigatório | ");
        }

        LOGGER.info("Validando estado");
        if(Objects.isNull(request.getState())) {
            LOGGER.warn("Estado inválido");
            sb.append("Estado inválido. É obrigatório | ");
        }

        LOGGER.info("Validando país");
        if(Objects.isNull(request.getCountry())) {
            LOGGER.warn("País inválido");
            sb.append("País inválido. É obrigatório");
        }

        if(sb.length() > 0) {
            throw new BadRequestException(sb.toString());
        }
    }
}
