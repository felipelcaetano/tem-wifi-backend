package br.com.temwifi.domains.auth.service;

import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.auth.TokenUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class ValidateTokenService implements Service<String, Void> {

    private static final Logger LOGGER = LogManager.getLogger(ValidateTokenService.class);

    @Inject
    public ValidateTokenService() {}

    /**
     * Validate a JWT token
     *
     * @param token
     * @return      void
     */
    @Override
    public Void execute(String token) {

        DecodedJWT jwt = TokenUtils.decodeToken(token);

        try {
            Algorithm algorithm = Algorithm.HMAC512(TokenUtils.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("temwifi")
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e){
            LOGGER.error("Erro ao validar token token", e);
            throw new IllegalArgumentException();
        }

        return null;
    }
}
