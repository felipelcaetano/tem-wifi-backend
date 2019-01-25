package br.com.temwifi.domains.infra.utils.exception;

public class HttpException extends Exception {

    private String message;

    public HttpException(String message) {
        super(message);
    }
}
