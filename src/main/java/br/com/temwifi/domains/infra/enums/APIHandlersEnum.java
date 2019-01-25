package br.com.temwifi.domains.infra.enums;

import java.util.Arrays;

public enum APIHandlersEnum {

    WARMUP_POST("/warmup", "POST", PostWarmUp.class),
    LOGIN_POST("/login", "POST", PostLogin.class),
    URL_PREASSINADA_POST("/urlpreassinada", "POST", PostUrlPreassinada.class),
    RECONHECIMENTO_IMAGEM_POST("/reconhecimentoimagem", "POST", PostReconhecimentoImagem.class),
    EXTRATOS_GET("/extrato", "GET", GetExtratos.class),
    EXTRATO_GET("/extrato/{extratoId}", "GET", GetExtrato.class),
    EXTRATO_POST("/extrato", "POST", PostExtrato.class),
    RELATORIO_CONSOLIDADO_GET("/relatorio/consolidado", "GET", GetRelatorioConsolidado.class);

    private String resource;
    private String httpMethod;
    private Class clazz;

    APIHandlersEnum(String resource, String httpMethod, Class clazz) {
        this.resource = resource;
        this.httpMethod = httpMethod;
        this.clazz = clazz;
    }

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
