package br.com.temwifi.domains.auth.service;

import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.auth.PasswordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class CreateUserService implements Service<PostUserRequest, UserDTO> {

    private static final Logger LOGGER = LogManager.getLogger(CreateUserService.class);

    private UserEntity userEntity;

    @Inject
    public CreateUserService(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public UserDTO execute(PostUserRequest request) {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        UserDTO user = new UserDTO();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        String salt = PasswordUtils.getSalt();
        user.setPass(PasswordUtils.generateSecurePassword(request.getPass(), salt));
        user.setSalt(salt);

        userEntity.createUser(user);

        return user;
    }

}
