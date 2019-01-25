package br.com.temwifi.domains.infra.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class RestAbstractResponse {

    @JsonIgnore
    protected Integer statusCode;
    protected List<Hypermedia> links;

    protected RestAbstractResponse() {
        this.statusCode = HttpStatus.SC_OK;
        this.links = new ArrayList<>();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Hypermedia> getLinks() {
        return links;
    }

    public void setLinks(List<Hypermedia> links) {
        this.links = links;
    }
}
