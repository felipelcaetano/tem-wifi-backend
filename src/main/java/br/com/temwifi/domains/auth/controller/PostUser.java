package br.com.temwifi.domains.auth.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.model.request.GetUserRequest;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.domains.auth.model.response.PostUserResponse;
import br.com.temwifi.domains.auth.service.CreateUserOrchestration;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Controller(auth = false)
public class PostUser implements AwsApiRestHandler<PostUserRequest, PostUserResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PostUser.class);

    private CreateUserOrchestration createUserOrchestration;

    public PostUser() {
        createUserOrchestration = DaggerAuthComponent.create().buildCreateUserOrchestration();
    }

    /**
     * Register a new user
     *
     * @param body
     * @param httpContext
     * @return              new user data + a token
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

        return createUserOrchestration.execute(body);
    }
}
