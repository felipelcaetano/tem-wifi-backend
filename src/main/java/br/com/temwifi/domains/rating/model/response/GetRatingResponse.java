package br.com.temwifi.domains.rating.model.response;

import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;

public class GetRatingResponse extends RestAbstractResponse {

    private String id;
    private String locationId;
    private String userId;
    private GetInrenetRatingResponse internet;
    private String foods;
    private String drinks;
    private Integer treatment;
    private Integer price;
    private Integer comfort;
    private Integer noise;
    private Integer generalRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GetInrenetRatingResponse getInternet() {
        return internet;
    }

    public void setInternet(GetInrenetRatingResponse internet) {
        this.internet = internet;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getDrinks() {
        return drinks;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }

    public Integer getTreatment() {
        return treatment;
    }

    public void setTreatment(Integer treatment) {
        this.treatment = treatment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getComfort() {
        return comfort;
    }

    public void setComfort(Integer comfort) {
        this.comfort = comfort;
    }

    public Integer getNoise() {
        return noise;
    }

    public void setNoise(Integer noise) {
        this.noise = noise;
    }

    public Integer getGeneralRating() {
        return generalRating;
    }

    public void setGeneralRating(Integer generalRating) {
        this.generalRating = generalRating;
    }
}
