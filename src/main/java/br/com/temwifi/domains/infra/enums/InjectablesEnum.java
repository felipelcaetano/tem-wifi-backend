package br.com.temwifi.domains.infra.enums;

import com.amazonaws.services.apigateway.model.BadRequestException;

import java.util.Arrays;
import java.util.Optional;

public enum InjectablesEnum {

    CONTROLLER("Controller"),
    SERVICE("Service"),
    ENTITY("Entity");

    private String injectableName;

    InjectablesEnum(String injectableName) {
        this.injectableName = injectableName;
    }

    public static InjectablesEnum getByName(String injectableName) {

        Optional<InjectablesEnum> injectablesEnumOptional = Arrays.asList(InjectablesEnum.values()).stream()
                .filter(injectableEnum -> injectableEnum.getInjectableName().equalsIgnoreCase(injectableName))
                .findFirst();

        if(!injectablesEnumOptional.isPresent()) {
            throw new BadRequestException(String.format("Injectable [%s] nao encontrado", injectableName));
        }

        return injectablesEnumOptional.get();
    }

    public String getInjectableName() {
        return this.injectableName;
    }
}
