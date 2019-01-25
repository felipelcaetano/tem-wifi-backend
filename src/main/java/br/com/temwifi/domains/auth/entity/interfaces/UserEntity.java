package br.com.temwifi.domains.auth.entity.interfaces;

import br.com.temwifi.domains.auth.model.User;

import java.util.Optional;

public interface UserEntity {

    void createUser(User user);

    Optional<User> readUserById(String id);

    Optional<User> readUserByEmail(String email);
}
