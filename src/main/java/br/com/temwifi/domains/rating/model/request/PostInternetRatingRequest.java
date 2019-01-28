package br.com.temwifi.domains.rating.model.request;

public class PostInternetRatingRequest {

    private Boolean hasInternet;
    private Integer speed;
    private Boolean isOpened;
    private String pass;

    public Boolean getHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(Boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(Boolean opened) {
        isOpened = opened;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
