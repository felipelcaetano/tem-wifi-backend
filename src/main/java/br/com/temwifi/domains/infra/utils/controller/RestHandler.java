package br.com.temwifi.domains.infra.utils.controller;

import br.com.temwifi.domains.infra.model.request.AWSHttpContext;
import br.com.temwifi.domains.infra.utils.exception.HttpException;

public interface RestHandler<I, O> {

    O handleRequest(I body, AWSHttpContext httpContext) throws HttpException;
}
