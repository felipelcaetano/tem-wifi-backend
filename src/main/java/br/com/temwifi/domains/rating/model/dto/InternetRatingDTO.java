package br.com.temwifi.domains.rating.model.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBDocument
public class InternetRatingDTO {

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean hasInternet;

    @DynamoDBAttribute
    private Integer speed;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean isOpened;

    @DynamoDBAttribute
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

    public Boolean getOpened() {
        return isOpened;
    }

    public void setOpened(Boolean opened) {
        isOpened = opened;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
