package br.com.temwifi.domains.infra.entity;

import br.com.temwifi.domains.infra.utils.factory.DynamoDBMapperFactory;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import java.util.Objects;
import java.util.Optional;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES;

public abstract class DynamoDBAbstractEntity {

    protected DynamoDBMapper mapper;

    protected DynamoDBAbstractEntity() {
        if(Objects.isNull(mapper))
            mapper = DynamoDBMapperFactory.getMapper();
    }

    /**
     * Insert or Update data into DynamoDB Table using DynamoDBMapper class
     * Do not consider null fields
     *
     * @param object    Object to be inserted or updated
     * @param <T>       Object's class
     */
    public <T> void save(T object) {
        this.mapper.save(object, DynamoDBMapperConfig.builder().withSaveBehavior(UPDATE_SKIP_NULL_ATTRIBUTES).build());
    }

    /**
     * Retrieve data from a DynamoDB Table using DynamoDBMapper class
     *
     * @param id        hashKey
     * @param clazz     Object's class to be returned
     * @param <T>       Object to be returned
     * @return
     */
    public <T> Optional<T> load(String id, Class clazz) {
        return (Optional<T>) Optional.ofNullable(this.mapper.load(clazz, id));
    }

    /**
     * Delete data of a DynamoDB Table using DynamoDBMapper class
     *
     * @param object    Object to be deleted
     * @param <T>
     */
    public <T> void delete(T object) {
        this.mapper.delete(object);
    }

}
