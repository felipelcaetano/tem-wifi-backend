package br.com.temwifi.domains.rating.model.request;

public class PutRatingRequest {

    private String id;
    private PutInternetRatingRequest internet;
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

    public PutInternetRatingRequest getInternet() {
        return internet;
    }

    public void setInternet(PutInternetRatingRequest internet) {
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
