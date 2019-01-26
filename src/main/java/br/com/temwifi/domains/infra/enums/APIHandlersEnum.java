package br.com.temwifi.domains.infra.enums;

import br.com.temwifi.domains.auth.controller.PostLogin;
import br.com.temwifi.domains.auth.controller.PostUser;
import br.com.temwifi.domains.infra.controller.PostWarmUp;

import java.util.Arrays;

public enum APIHandlersEnum {

    WARMUP_POST("/infra/warmup", "POST", PostWarmUp.class),
    USER_POST("/auth/user", "POST", PostUser.class),
    LOGIN_POST("/auth/login", "POST", PostLogin.class);

    private String resource;
    private String httpMethod;
    private Class clazz;

    APIHandlersEnum(String resource, String httpMethod, Class clazz) {
        this.resource = resource;
        this.httpMethod = httpMethod;
        this.clazz = clazz;
    }

    /**
     * Try to find the controller responsible for handling a request based on resource path and http method
     *
     * @param resource
     * @param httpMethod
     * @return              controller class name
     */
    public static APIHandlersEnum getAPIResourcesEnum(String resource, String httpMethod) {

        return Arrays.asList(APIHandlersEnum.values())
                .stream()
                .filter(e -> e.getAPIResouce().equalsIgnoreCase(resource) && e.getHttpMethod().equalsIgnoreCase(httpMethod))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Resource nao encontrado"));
    }

    public String getAPIResouce() {
        return resource;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Class getClazz() {
        return clazz;
    }

}
