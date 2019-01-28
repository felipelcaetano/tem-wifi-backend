package br.com.temwifi.domains.locations.model.response;

import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;

import java.util.Set;

public class GetLocationResponse extends RestAbstractResponse {

    private String id;
    private String name;
    private String street;
    private String number;
    private String complement;
    private String postCode;
    private String city;
    private String state;
    private String country;
    private Integer ratingsCount;
    private Set<String> ratings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public Set<String> getRatings() {
        return ratings;
    }

    public void setRatings(Set<String> ratings) {
        this.ratings = ratings;
    }
}
