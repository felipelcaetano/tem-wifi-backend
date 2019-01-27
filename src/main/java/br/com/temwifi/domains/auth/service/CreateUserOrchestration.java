package br.com.temwifi.domains.auth.service;

import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.auth.model.request.GetUserRequest;
import br.com.temwifi.domains.auth.model.request.PostLoginRequest;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.domains.auth.model.response.PostLoginResponse;
import br.com.temwifi.domains.auth.model.response.PostUserResponse;
import br.com.temwifi.domains.infra.model.response.Hypermedia;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;

public class CreateUserOrchestration implements Service<PostUserRequest, PostUserResponse> {

    private static final Logger LOGGER = LogManager.getLogger(CreateUserOrchestration.class);

    private LoginService loginService;
    private ReadUserService readUserService;
    private CreateUserService createUserService;

    @Inject
    public CreateUserOrchestration() {
        createUserService = DaggerAuthComponent.create().buildCreateUserService();
        readUserService = DaggerAuthComponent.create().buildReadUserService();
        loginService = DaggerAuthComponent.create().buildLoginService();
    }

    /**
     * Orchestrate the calls in order to register a new user
     *
     * @param request
     * @return                  user's data + a token
     * @throws HttpException
     */
    @Override
    public PostUserResponse execute(PostUserRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(request.getEmail());

        try {
            readUserService.execute(getUserRequest);
            throw new BadRequestException("Usuário já cadastrado");
        } catch (ResourceNotFoundException e){
            LOGGER.info("Usuário não cadastrado");
        }

        LOGGER.info("Cadastrando usuário");
        UserDTO user = createUserService.execute(request);

        LOGGER.info("Usuário cadastrado com sucesso");
        LOGGER.info("Obtendo token");
        PostLoginRequest postLoginRequest = new PostLoginRequest();
        postLoginRequest.setUser(user.getEmail());
        postLoginRequest.setPass(request.getPass());
        PostLoginResponse loginResponse = loginService.execute(postLoginRequest);

        LOGGER.info("Token obtido com sucesso");
        PostUserResponse postUserResponse = new PostUserResponse();
        postUserResponse.setStatusCode(HttpStatus.SC_CREATED);

        postUserResponse.setEmail(user.getEmail());
        postUserResponse.setId(user.getId());
        postUserResponse.setToken(loginResponse.getToken());

        Hypermedia hypermedia = new Hypermedia();
        hypermedia.setHref("user/".concat(user.getId()));
        hypermedia.setRel(user.getId());
        postUserResponse.setLinks(Arrays.asList(hypermedia));

        return postUserResponse;
    }
}
