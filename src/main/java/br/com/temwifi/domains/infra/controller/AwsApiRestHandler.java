package br.com.temwifi.domains.infra.controller;

import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;

public interface AwsApiRestHandler<I, O> {

    /**
     * Main method of each Aws Controller Class
     *
     * @param body
     * @param httpContext
     * @return
     * @throws HttpException
     */
    O handleRequest(I body, AwsHttpContext httpContext) throws HttpException;
}
