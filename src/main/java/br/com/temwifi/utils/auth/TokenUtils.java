package br.com.temwifi.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.xml.bind.DatatypeConverter;

public class TokenUtils {

    private static final String JWT_SECRET = "JWT_SECRET";

    /**
     * Generate a base64 secret hash
     *
     * @return  a base64 hash
     */
    public static byte[] getSecret() {
        return DatatypeConverter.parseBase64Binary(System.getenv(JWT_SECRET));
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
