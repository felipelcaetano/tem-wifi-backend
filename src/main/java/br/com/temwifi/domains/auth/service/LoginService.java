package br.com.temwifi.domains.auth.service;

import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.enums.AuthProviderEnum;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.auth.model.request.PostLoginRequest;
import br.com.temwifi.domains.auth.model.response.PostLoginResponse;
import br.com.temwifi.domains.infra.utils.exception.BadRequestException;
import br.com.temwifi.domains.infra.utils.exception.HttpException;
import br.com.temwifi.domains.infra.utils.exception.InternalServerErrorException;
import br.com.temwifi.domains.infra.utils.exception.ResourceNotFoundException;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.MapperUtils;
import br.com.temwifi.utils.auth.PasswordUtils;
import br.com.temwifi.utils.auth.TokenUtils;
import br.com.temwifi.utils.date.LocalDateTimeUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class LoginService implements Service<PostLoginRequest, PostLoginResponse> {

    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    private UserEntity userEntity;

    @Inject
    public LoginService(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Validate email and pass and generate a token if valid
     *
     * @param request
     * @return                  user's data + a token
     * @throws HttpException
     */
    @Override
    public PostLoginResponse execute(PostLoginRequest request) throws HttpException {

        LOGGER.info(String.format("%s executing", this.getClass().getSimpleName()));
        LOGGER.info(String.format("Request: %s", MapperUtils.toJson(request)));

        Optional<UserDTO> user = userEntity.readUserByEmail(request.getUser());

        if(AuthProviderEnum.TEMWIFI.toString().equalsIgnoreCase(request.getProvider())) {

            if(!user.isPresent()) {
                LOGGER.error(String.format("Usuário [%s] não encontrada", request.getUser()));
                throw new ResourceNotFoundException(String.format("Usuário [%s] não encontrado", request.getUser()));
            }

            if(!PasswordUtils.verifyUserPassword(request.getPass(), user.get().getPass(), user.get().getSalt())) {
                LOGGER.error("Usuário ou senha inválidos");
                throw new BadRequestException("Usuário ou senha inválidos");
            }
        } else if(!user.isPresent()){

            UserDTO userDTO = new UserDTO();
            userDTO.setInsertDateTime(LocalDateTimeUtils.now());
            userDTO.setEmail(request.getUser());
            userDTO.setId(UUID.randomUUID().toString());
            userEntity.createUser(userDTO);
        }

        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC512(TokenUtils.getSecret());
            token = JWT.create()
                    .withIssuer("temwifi")
                    .withClaim("id", user.get().getId())
                    .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                    .withIssuedAt(Date.from(Instant.now()))
                    .sign(algorithm);
        } catch (JWTCreationException e){
            LOGGER.error("Erro ao gerar token", e);
            throw new InternalServerErrorException();
        }

        PostLoginResponse postLoginResponse = new PostLoginResponse();
        postLoginResponse.setToken(token);
        postLoginResponse.setId(user.get().getId());
        return postLoginResponse;
    }
}
