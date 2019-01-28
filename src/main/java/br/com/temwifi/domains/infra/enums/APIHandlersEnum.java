package br.com.temwifi.domains.infra.enums;

import br.com.temwifi.domains.auth.controller.PostLogin;
import br.com.temwifi.domains.auth.controller.PostUser;
import br.com.temwifi.domains.infra.controller.PostWarmUp;
import br.com.temwifi.domains.locations.controller.GetLocation;
import br.com.temwifi.domains.locations.controller.PostLocation;
import br.com.temwifi.domains.rating.controller.GetRating;
import br.com.temwifi.domains.rating.controller.GetRatings;
import br.com.temwifi.domains.rating.controller.PostRating;
import br.com.temwifi.domains.rating.controller.PutRating;

import java.util.Arrays;

public enum APIHandlersEnum {

    WARMUP_POST("/infra/warmup", "POST", PostWarmUp.class),
    LOGIN_POST("/auth/login", "POST", PostLogin.class),
    USER_POST("/auth/user", "POST", PostUser.class),
    LOCATION_GET_ID("/location/{locationId}", "GET", GetLocation.class),
    LOCATION_POST("/location", "POST", PostLocation.class),
    RATING_GET("/rating", "GET", GetRatings.class),
    RATING_GET_ID("/rating/{ratingId}", "GET", GetRating.class),
    RATING_POST("/rating", "POST", PostRating.class),
    RATING_PUT_ID("/rating/{ratingId}", "PUt", PutRating.class);

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
