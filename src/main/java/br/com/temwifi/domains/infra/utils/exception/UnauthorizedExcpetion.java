package br.com.temwifi.domains.infra.utils.exception;

public class UnauthorizedExcpetion extends HttpException {

    public UnauthorizedExcpetion() {
        super("Não autorizado");
    }

    public UnauthorizedExcpetion(String message) {
        super(message);
    }
}
