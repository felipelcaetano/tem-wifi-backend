package br.com.temwifi.domains.infra.utils.exception;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(message);
    }
}
