package br.com.temwifi.domains.auth.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.auth.component.DaggerAuthComponent;
import br.com.temwifi.domains.auth.enums.AuthProviderEnum;
import br.com.temwifi.domains.auth.model.request.PostLoginRequest;
import br.com.temwifi.domains.auth.model.response.PostLoginResponse;
import br.com.temwifi.domains.auth.service.LoginService;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Controller(auth = false)
public class PostLogin implements AwsApiRestHandler<PostLoginRequest, PostLoginResponse> {

    private static final Logger LOGGER = LogManager.getLogger(PostLogin.class);

    private LoginService service;

    public PostLogin() {
        service = DaggerAuthComponent.create().buildLoginService();
    }

    /**
     * Validate email and pass and generate a token if valid
     *
     * @param body
     * @param httpContext
     * @return              user's data + a token
     * @throws HttpException
     */
    @Override
    public PostLoginResponse handleRequest(PostLoginRequest body, AwsHttpContext httpContext) throws HttpException {

        if(Objects.isNull(body) || Objects.isNull(body.getUser()) || Objects.isNull(body.getProvider())) {
            LOGGER.error(String.format("Requisição inválida | %s", MapperUtils.toJson(body)));
            throw new BadRequestException("Email e/ou Provider não informados");
        }

        if(AuthProviderEnum.TEMWIFI.toString().equalsIgnoreCase(body.getProvider()) && Objects.isNull(body.getPass())) {
            LOGGER.error(String.format("Requisição inválida | %s", MapperUtils.toJson(body)));
            throw new BadRequestException("Senha não informada");
        }

        return service.execute(body);
    }
}
