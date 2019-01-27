package br.com.temwifi.domains.rating.entity.interfaces;

import br.com.temwifi.domains.rating.model.dto.RatingDTO;

import java.util.List;
import java.util.Optional;

public interface RatingEntity {

    /**
     * Create e new rating for a location
     *
     * @param rating
     */
    void createRating(RatingDTO rating);

    /**
     * Get a rating by it's id
     *
     * @param id
     * @return      a rating dto
     */
    Optional<RatingDTO> readRatingById(String id);

    /**
     * Get a ratings by it's location id
     *
     * @param locationId
     * @return                  a list of ratings dto
     */
    List<RatingDTO> readRatingsByLocationId(String locationId);

    /**
     * Get a ratings by it's user id
     *
     * @param userId
     * @return                  a list of ratings dto
     */
    List<RatingDTO> readRatingsByUserId(String userId);
}
