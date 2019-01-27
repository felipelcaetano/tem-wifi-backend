package br.com.temwifi.utils.cep;

import br.com.temwifi.interfaces.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ViaCepService implements Service<String, Void> {

    /**
     * Check if a brazilian post code of an address is valid based on Via CEP web service
     *
     * @param cep
     * @return          void
     */
    public Void execute(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return null;
    }
}
