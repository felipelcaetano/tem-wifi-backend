package br.com.temwifi.interfaces;

@FunctionalInterface
public interface Service<I, O> {

   O execute(I request) throws Exception;
}
