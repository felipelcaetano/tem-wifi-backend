package br.com.temwifi.domains.locations.entity.interfaces;

import br.com.temwifi.domains.locations.model.dto.LocationDTO;

import java.util.Optional;

public interface LocationEntity {

    /**
     * Create e new location
     *
     * @param location
     */
    void createLocation(LocationDTO location);

    /**
     * Get a location by it's id
     *
     * @param id
     * @return      a location dto
     */
    Optional<LocationDTO> readLocationById(String id);

    /**
     * Get a location by it's complete address
     *
     * @param completeAddress
     * @return                  a location dto
     */
    Optional<LocationDTO> readLocationByCompleteAddress(String completeAddress);

    /**
     * Get a location by it's formatted name
     *
     * @param name
     * @return                  a location dto
     */
    Optional<LocationDTO> readLocationByName(String name);
}
