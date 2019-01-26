package br.com.temwifi.interfaces;

@FunctionalInterface
public interface Service<I, O> {

   /**
    * Single and only method in each service implementation
    *
    * @param request    service input
    * @return           service output
    * @throws Exception
    */
   O execute(I request) throws Exception;
}
