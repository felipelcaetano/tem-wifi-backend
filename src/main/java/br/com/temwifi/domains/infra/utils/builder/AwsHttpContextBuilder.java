package br.com.temwifi.domains.infra.utils.builder;

import br.com.temwifi.domains.infra.model.request.AwsApiRequest;
import br.com.temwifi.domains.infra.model.request.AwsHttpContext;

import java.util.Collections;
import java.util.Map;

public class AwsHttpContextBuilder {

    /**
     * Generate a AwsHttpContext based on AwsApiRequest containing all data from ApiGateway request
     *
     * @param request
     * @return          a instance of AwsHttpContext
     */
    public static AwsHttpContext build(AwsApiRequest request) {

        AwsHttpContext httpContext = new AwsHttpContext();
        httpContext.setHeaders(request.getHeaders());
        httpContext.setHttpMethod(request.getHttpMethod());
        httpContext.setPath(request.getPath());
        httpContext.setPathParameters(request.getPathParameters());
        httpContext.setQueryStringParameters(request.getQueryStringParameters());
        httpContext.setRequestContext(request.getRequestContext());
        httpContext.setResource(request.getResource());

        Map<String, String> stageVariables = httpContext.getStageVariables();
        if(stageVariables == null || stageVariables.isEmpty()) {
            httpContext.setStageVariables(Collections.singletonMap("env", "dev"));
        } else {
            httpContext.setStageVariables(request.getStageVariables());
        }

        return httpContext;
    }
}
