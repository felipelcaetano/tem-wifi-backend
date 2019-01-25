package br.com.temwifi.domains.auth.model.response;

import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;

public class PostLoginResponse extends RestAbstractResponse {

    private String userId;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
