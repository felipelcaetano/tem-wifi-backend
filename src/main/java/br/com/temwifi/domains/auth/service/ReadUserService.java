package br.com.temwifi.domains.auth.service;

import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.auth.model.request.GetUserRequest;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class ReadUserService implements Service<GetUserRequest, UserDTO> {

    private static final Logger LOGGER = LogManager.getLogger(ReadUserService.class);

    private UserEntity userEntity;

    @Inject
    public ReadUserService(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Get a user by it's email
     *
     * @param request
     * @return          the user's data
     * @throws ResourceNotFoundException
     */
    @Override
    public UserDTO execute(GetUserRequest request) throws ResourceNotFoundException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        Optional<UserDTO> user = userEntity.readUserByEmail(request.getEmail());

        if(!user.isPresent()) {
            throw new ResourceNotFoundException(String.format("Usuário [%s] não encontrado", request.getEmail()));
        }

        return user.get();
    }

}