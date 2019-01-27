package br.com.temwifi.domains.infra.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.service.ValidateTokenService;
import br.com.temwifi.domains.infra.enums.APIHandlersEnum;
import br.com.temwifi.domains.infra.model.request.AwsApiRequest;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;
import br.com.temwifi.domains.infra.model.response.AwsApiResponse;
import br.com.temwifi.domains.infra.model.response.error.ErrorResponse;
import br.com.temwifi.domains.infra.utils.builder.AwsHttpContextBuilder;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.InternalServerErrorException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.domains.infra.utils.exception.UnauthorizedExcpetion;
import br.com.temwifi.utils.MapperUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class AwsApiRequestHandler implements RequestHandler<AwsApiRequest, AwsApiResponse> {

    private static Logger LOGGER = LogManager.getLogger(AwsApiRequestHandler.class);
    private static final String HANDLE_REQUEST = "handleRequest";
    private static final String AUTHORIZATION = "Authorization";

    private ObjectMapper objectMapper;
    private ValidateTokenService validateTokenService;

    public AwsApiRequestHandler() {
        validateTokenService = DaggerAuthComponent.create().buildValidateTokenService();
    }

    /**
     * Handle all requests originated from ApiGateway
     * Find the controller responsible for handling the request and execute it
     *
     * @param apiRequest
     * @param context
     * @return              generic response with a specific body from the controller
     */
    @Override
    public AwsApiResponse handleRequest(AwsApiRequest apiRequest, Context context) {

        LOGGER.info(String.format("Request: \n%s", MapperUtils.toJson(apiRequest)));

        String resource = apiRequest.getResource();
        String httpMethod = apiRequest.getHttpMethod();

        APIHandlersEnum apiResourcesEnum = APIHandlersEnum.getAPIResourcesEnum(resource, httpMethod);
        LOGGER.info(String.format("Resource: %s", apiResourcesEnum.getClazz().getSimpleName()));

        Method handleRequest;
        try {
            handleRequest = getHandleRequest(apiResourcesEnum);
        } catch (Exception e) {
            return getAPIResponseError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Erro inesperado");
        }

        try {
            validateAuth(apiRequest.getHeaders(), apiResourcesEnum.getClazz());
        } catch (UnauthorizedExcpetion e) {
            return getAPIResponseError(HttpStatus.SC_UNAUTHORIZED, e.getMessage());
        }

        Object controller = getClassInstance(apiResourcesEnum);
        AwsHttpContext httpContext = AwsHttpContextBuilder.build(apiRequest);

        Class inputType = handleRequest.getParameterTypes()[0];

        Object requestBody;
        try {
            requestBody = deserializeRequest(inputType, apiRequest.getBody());
        } catch (Exception e) {
            return getAPIResponseError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Erro inesperado");
        }

        AwsApiResponse apiResponse = invokeMethod(handleRequest, controller, requestBody, httpContext);

        LOGGER.info(String.format("Response: \n%s", MapperUtils.toJson(apiResponse)));
        return apiResponse;
    }

    /**
     * Instantiate a new controller object
     *
     * @param apiHandlersEnum
     * @return                  controller instance
     */
    private Object getClassInstance(APIHandlersEnum apiHandlersEnum) {

        try {
            return apiHandlersEnum.getClazz().getConstructors()[0].newInstance();
        } catch (Exception e) {
            LOGGER.error(String.format("Erro ao gerar intancia do handler %s", apiHandlersEnum.getClazz().getName()));
            LOGGER.error(e);
            return getAPIResponseError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Erro inesperado");
        }
    }

    /**
     * Find the handleRequest method in the controller class
     *
     * @param apiHandlersEnum
     * @return                  handleRequest Method
     * @throws InternalServerErrorException
     */
    private Method getHandleRequest(APIHandlersEnum apiHandlersEnum) throws InternalServerErrorException {

        Class clazz = apiHandlersEnum.getClazz();

        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Class parameterizedTypeInput = (Class) parameterizedType.getActualTypeArguments()[0];
        try {
            return clazz.getDeclaredMethod(HANDLE_REQUEST, parameterizedTypeInput, AwsHttpContext.class);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e);
            throw new InternalServerErrorException(
                    String.format("Método handleRequest nao implementado na classe [%s]", clazz.getSimpleName()));
        }
    }

    /**
     * Execute controller's handleRequest method
     *
     * @param handleRequest     method
     * @param controller
     * @param input             request body
     * @param httpContext       http data
     * @return                  default response with specific body
     */
    private AwsApiResponse invokeMethod(Method handleRequest, Object controller, Object input, AwsHttpContext httpContext) {

        AwsApiResponse apiResponse = new AwsApiResponse();

        try {
            RestAbstractResponse response = (RestAbstractResponse) handleRequest.invoke(controller, input, httpContext);
            apiResponse.setBody(response);
            apiResponse.setStatusCode(response.getStatusCode());
            return apiResponse;

        } catch (Exception e) {
            if(e.getCause() != null && e.getCause() instanceof BadRequestException) {
                ErrorResponse errorResponse = new ErrorResponse(e.getCause().getMessage());
                apiResponse.setBody(errorResponse);
                apiResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                return apiResponse;
            }

            if(e.getCause() != null && e.getCause() instanceof UnauthorizedExcpetion) {
                ErrorResponse errorResponse = new ErrorResponse(e.getCause().getMessage());
                apiResponse.setBody(errorResponse);
                apiResponse.setStatusCode(HttpStatus.SC_UNAUTHORIZED);
                return apiResponse;
            }

            if(e.getCause() != null && e.getCause() instanceof ResourceNotFoundException) {
                ErrorResponse errorResponse = new ErrorResponse(e.getCause().getMessage());
                apiResponse.setBody(errorResponse);
                apiResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
                return apiResponse;
            }

            LOGGER.error(String.format("Erro inesperado no processamento do request pela classe %s", controller.getClass().getSimpleName()));
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getCause());
            if(e.getCause() != null) e.getCause().printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Erro interno");
            apiResponse.setBody(errorResponse);
            apiResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return apiResponse;
        }
    }

    /**
     * Generate a default response with error in it
     *
     * @param statusCode
     * @param message
     * @return              new instance of AwsApiResponse with detailed error
     */
    private AwsApiResponse getAPIResponseError(Integer statusCode, String message) {

        AwsApiResponse apiResponse = new AwsApiResponse();
        apiResponse.setStatusCode(statusCode);
        apiResponse.setBody(message);
        return apiResponse;
    }

    /**
     * Instatiate new ObjectMapper
     *
     * @return      new ObjectMapper instance
     */
    private ObjectMapper getObjectMapper() {
        if(objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        return objectMapper;
    }

    /**
     * Transform request body into a controller input class
     *
     * @param clazz
     * @param body
     * @return          new isntance of controller input class
     * @throws InternalServerErrorException
     */
    private Object deserializeRequest(Class clazz, String body) throws InternalServerErrorException {
        if(StringUtils.isNullOrEmpty(body)) {
            return null;
        }

        try {
            return getObjectMapper().readValue(body, clazz);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new InternalServerErrorException(
                    String.format("Erro inesperado ao deserializar o request body para a classe %s", clazz.getSimpleName()));
        }
    }

    /**
     * Check request Authorization header when based on Controller annotation in Controller class
     *
     * @param headers
     * @param clazz
     * @throws UnauthorizedExcpetion
     */
    private void validateAuth(Map<String, String> headers, Class<?> clazz) throws UnauthorizedExcpetion {

        Boolean auth = clazz.getAnnotation(Controller.class).auth();

        if(auth) {
            if(headers == null || !headers.containsKey(AUTHORIZATION)) {
                LOGGER.error("Token não informado");
                throw new UnauthorizedExcpetion();
            }

            try {
                validateTokenService.execute(headers.get(AUTHORIZATION));
            } catch (Exception e) {
                LOGGER.error("Erro ao validar token", e);
                throw new UnauthorizedExcpetion();
            }
        }
    }
}

