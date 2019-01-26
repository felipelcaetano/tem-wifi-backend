package br.com.temwifi.utils;

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {

    /**
     * Transfor an object into a json string
     *
     * @param object
     * @return          json string
     */
    public static String toJson(Object object) {

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
