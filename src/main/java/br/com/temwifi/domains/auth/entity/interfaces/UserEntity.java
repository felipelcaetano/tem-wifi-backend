package br.com.temwifi.domains.auth.entity.interfaces;

import br.com.temwifi.domains.auth.model.dto.UserDTO;

import java.util.Optional;

public interface UserEntity {

    /**
     * Create e new user
     *
     * @param user
     */
    void createUser(UserDTO user);

    /**
     * Get a user by it's id
     *
     * @param id
     * @return      an user dto
     */
    Optional<UserDTO> readUserById(String id);

    /**
     * Get a user by it's email
     *
     * @param email
     * @return      an user dto
     */
    Optional<UserDTO> readUserByEmail(String email);
}
