package br.com.temwifi.utils.auth;

import javax.xml.bind.DatatypeConverter;

public class TokenUtils {

    public static byte[] getSecret() {
        return DatatypeConverter.parseBase64Binary("5f0181cd-fddf-4350-820e-92d080faa5c6");
    }
}
