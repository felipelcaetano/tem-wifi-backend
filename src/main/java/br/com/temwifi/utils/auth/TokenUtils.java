package br.com.temwifi.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.xml.bind.DatatypeConverter;

public class TokenUtils {

    /**
     * Generate a base64 secret hash
     *
     * @return  a base64 hash
     */
    public static byte[] getSecret() {
        return DatatypeConverter.parseBase64Binary("5f0181cd-fddf-4350-820e-92d080faa5c6");
    }

    /**
     * Decode a token into a JWT pattern
     *
     * @param token
     * @return      decoded token
     */
    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }
}
