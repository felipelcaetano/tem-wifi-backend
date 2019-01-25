package br.com.temwifi.domains.infra.controller;

import br.com.temwifi.domains.infra.model.request.AwsHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;

public interface AwsApiRestHandler<I, O> {

    O handleRequest(I body, AwsHttpContext httpContext) throws HttpException;
}
