package br.com.temwifi.domains.infra.controller;

import br.com.temwifi.annotations.Controller;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.model.response.PostWarmUpResponse;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import org.apache.http.HttpStatus;

@Controller(auth = false)
public class PostWarmUp implements AwsApiRestHandler<Void, PostWarmUpResponse> {

    /**
     * Fake a call to lambda function in order to minimize lambda warmup time for next calls
     *
     * @param body              void
     * @param httpContext       http data
     * @return                  http data
     * @throws HttpException
     */
    @Override
    public PostWarmUpResponse handleRequest(Void body, AwsHttpContext httpContext) throws HttpException {
        PostWarmUpResponse postWarmUpResponse = new PostWarmUpResponse();
        postWarmUpResponse.setStatusCode(HttpStatus.SC_NO_CONTENT);
        return postWarmUpResponse;
    }
}
