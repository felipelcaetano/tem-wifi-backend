package br.com.temwifi.domains.auth.model.response;

import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;

public class PostUserResponse extends RestAbstractResponse {

    private String id;
    private String email;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
