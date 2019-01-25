package br.com.temwifi.domains.infra.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import java.util.Optional;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES;

public abstract class DynamoDBAbstractEntity {

    protected DynamoDBMapper mapper;

    public <T> void save(T object) {
        this.mapper.save(object, DynamoDBMapperConfig.builder().withSaveBehavior(UPDATE_SKIP_NULL_ATTRIBUTES).build());
    }

    public <T> Optional<T> load(String id, Class clazz) {
        return (Optional<T>) Optional.ofNullable(this.mapper.load(clazz, id));
    }

    public <T> void delete(T object) {
        this.mapper.delete(object);
    }

}
