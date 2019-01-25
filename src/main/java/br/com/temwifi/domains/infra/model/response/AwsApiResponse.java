package br.com.temwifi.domains.infra.model.response;

import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class AwsApiResponse<T> {

    private boolean isBase64Encoded;
    private int statusCode;
    private Map<String, String> headers;
    private String body;

    public AwsApiResponse(T body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        setHeaders(headers);
        setStatusCode(HttpStatus.SC_OK);
        setBody(body);
    }

    public AwsApiResponse(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        setHeaders(headers);
        setStatusCode(HttpStatus.SC_OK);
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = new Gson().toJson(body);
    }
}
