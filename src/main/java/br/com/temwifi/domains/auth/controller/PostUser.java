package br.com.temwifi.domains.auth.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.auth.entity.impl.DynamoDBUserEntity;
import br.com.temwifi.domains.auth.model.User;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.domains.auth.model.response.PostUserResponse;
import br.com.temwifi.domains.auth.service.CreateUserService;
import br.com.temwifi.domains.infra.controller.AwsApiRestHandler;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.model.response.Hypermedia;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
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

    @Override
    public PostUserResponse handleRequest(PostUserRequest body, AwsHttpContext httpContext) throws HttpException {

        if(Objects.isNull(body) || Objects.isNull(body.getEmail()) || Objects.isNull(body.getPass())) {
            LOGGER.error(String.format("Requisição inválida | %s", MapperUtils.toJson(body)));
            throw new BadRequestException("Email e/ou senha não informados");
        }

        DynamoDBUserEntity dynamoDBUserEntity = new DynamoDBUserEntity();
        createUserService = new CreateUserService(dynamoDBUserEntity);
        User user = createUserService.execute(body);

        PostUserResponse postUserResponse = new PostUserResponse();
        postUserResponse.setStatusCode(HttpStatus.SC_CREATED);

        Hypermedia hypermedia = new Hypermedia();
        hypermedia.setHref("user/".concat(user.getId()));
        hypermedia.setRel(user.getId());
        postUserResponse.setLinks(Arrays.asList());

        return postUserResponse;
    }
}
