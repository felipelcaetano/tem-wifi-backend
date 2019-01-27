package br.com.temwifi.domains.rating.entity.impl;

import br.com.temwifi.domains.infra.entity.DynamoDBAbstractEntity;
import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import br.com.temwifi.domains.rating.model.dto.RatingDTO;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Optional;

public class DynamoDBRatingEntity extends DynamoDBAbstractEntity implements RatingEntity {

    private String locationIdIndex = "locationId-index";
    private String userIdIndex = "userId-index";

    @Override
    public void createRating(RatingDTO rating) {
        super.save(rating);
    }

    @Override
    public Optional<RatingDTO> readRatingById(String id) {
        return super.load(id, RatingDTO.class);
    }

    @Override
    public List<RatingDTO> readRatingsByLocationId(String locationId) {

        RatingDTO rating = new RatingDTO();
        rating.setLocationId(locationId);

        DynamoDBQueryExpression<RatingDTO> expression = new DynamoDBQueryExpression<RatingDTO>()
                .withIndexName(locationIdIndex)
                .withHashKeyValues(rating)
                .withConsistentRead(false);

        return mapper.query(RatingDTO.class, expression);
    }

    @Override
    public List<RatingDTO> readRatingsByUserId(String userId) {

        RatingDTO rating = new RatingDTO();
        rating.setUserId(userId);

        DynamoDBQueryExpression<RatingDTO> expression = new DynamoDBQueryExpression<RatingDTO>()
                .withIndexName(userIdIndex)
                .withHashKeyValues(rating)
                .withConsistentRead(false);

        return mapper.query(RatingDTO.class, expression);
    }
}
