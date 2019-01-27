package br.com.temwifi.domains.locations.entity.impl;

import br.com.temwifi.domains.infra.entity.DynamoDBAbstractEntity;
import br.com.temwifi.domains.locations.entity.interfaces.LocationEntity;
import br.com.temwifi.domains.locations.model.dto.LocationDTO;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Optional;

public class DynamoDBLocationEntity extends DynamoDBAbstractEntity implements LocationEntity {

    private String completeAddressIndex = "completeAddress-index";

    @Override
    public void createLocation(LocationDTO location) {
        super.save(location);
    }

    @Override
    public Optional<LocationDTO> readLocationById(String id) {
        return super.load(id, LocationDTO.class);
    }

    @Override
    public Optional<LocationDTO> readLocationByCompleteAddress(String completeAddress) {

        LocationDTO location = new LocationDTO();
        location.setCompleteAddress(completeAddress);

        DynamoDBQueryExpression<LocationDTO> expression = new DynamoDBQueryExpression<LocationDTO>()
                .withIndexName(completeAddressIndex)
                .withHashKeyValues(location)
                .withConsistentRead(false);

        List<LocationDTO> users = mapper.query(LocationDTO.class, expression);

        if(users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }
}
