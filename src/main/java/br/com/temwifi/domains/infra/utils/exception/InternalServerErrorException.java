package br.com.temwifi.domains.infra.utils.exception;

public class InternalServerErrorException extends HttpException {

    public InternalServerErrorException() {
        super("Erro interno");
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
