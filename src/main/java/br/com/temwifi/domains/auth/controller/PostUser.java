package br.com.temwifi.domains.auth.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.auth.model.request.GetUserRequest;
import br.com.temwifi.domains.auth.model.request.PostLoginRequest;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.domains.auth.model.response.PostLoginResponse;
import br.com.temwifi.domains.auth.model.response.PostUserResponse;
import br.com.temwifi.domains.auth.service.CreateUserService;
import br.com.temwifi.domains.auth.service.LoginService;
import br.com.temwifi.domains.auth.service.ReadUserService;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.model.response.Hypermedia;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.utils.MapperUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Objects;

@Controller(auth = false)
public class PostUser implements AwsApiRestHandler<PostUserRequest, PostUserResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PostUser.class);

    private CreateUserService createUserService;
    private LoginService loginService;
    private ReadUserService readUserService;

    public PostUser() {
        createUserService = DaggerAuthComponent.create().buildCreateUserService();
        readUserService = DaggerAuthComponent.create().buildReadUserService();
        loginService = DaggerAuthComponent.create().buildLoginService();
    }

    /**
     * Register a new user
     *
     * @param body
     * @param httpContext
     * @return new user data + a token
     * @throws HttpException
     */
    @Override
    public PostUserResponse handleRequest(PostUserRequest body, AwsHttpContext httpContext) throws HttpException {

        if(Objects.isNull(body) || Objects.isNull(body.getEmail()) || Objects.isNull(body.getPass())) {
            LOGGER.error(String.format("Requisição inválida | %s", MapperUtils.toJson(body)));
            throw new BadRequestException("Email e/ou senha não informados");
        }

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(body.getEmail());

        try {
            readUserService.execute(getUserRequest);
            throw new BadRequestException("Usuário já cadastrado");
        } catch (ResourceNotFoundException e){
            LOGGER.info("Usuário não cadastrado");
        }

        LOGGER.info("Cadastrando usuário");
        UserDTO user = createUserService.execute(body);

        LOGGER.info("Usuário cadastrado com sucesso");
        LOGGER.info("Obtendo token");
        PostLoginRequest postLoginRequest = new PostLoginRequest();
        postLoginRequest.setUser(user.getEmail());
        postLoginRequest.setPass(body.getPass());
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
