package br.com.temwifi.domains.rating.model.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TemWiFiRating")
public class RatingDTO {

    @DynamoDBHashKey
    private String id;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "locationId-index")
    private String locationId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userId-index")
    private String userId;

    @DynamoDBAttribute
    private InternetRatingDTO internet;

    @DynamoDBAttribute
    private String foods;

    @DynamoDBAttribute
    private String drinks;

    @DynamoDBAttribute
    private Integer treatment;

    @DynamoDBAttribute
    private Integer price;

    @DynamoDBAttribute
    private Integer comfort;

    @DynamoDBAttribute
    private Integer noise;

    @DynamoDBAttribute
    private Integer generalRating;

    @DynamoDBAttribute
    private String insertDateTime;

    @DynamoDBAttribute
    private String updateDateTime;

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

    public InternetRatingDTO getInternet() {
        return internet;
    }

    public void setInternet(InternetRatingDTO internet) {
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

    public String getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
