package br.com.temwifi.domains.infra.utils.exception;

public class ResourceNotFoundException extends HttpException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
