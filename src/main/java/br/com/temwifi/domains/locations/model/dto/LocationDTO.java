package br.com.temwifi.domains.locations.model.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Set;

@DynamoDBTable(tableName = "TemWiFiLocation")
public class LocationDTO {

    @DynamoDBHashKey
    private String id;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "completeAddress-index")
    private String completeAddress;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "name-index")
    private String nameIndex;

    @DynamoDBAttribute
    private String type;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String street;

    @DynamoDBAttribute
    private String number;

    @DynamoDBAttribute
    private String complement;

    @DynamoDBAttribute
    private String postCode;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private String state;

    @DynamoDBAttribute
    private String country;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
    private Set<String> ratings;

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

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(String nameIndex) {
        this.nameIndex = nameIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set<String> getRatings() {
        return ratings;
    }

    public void setRatings(Set<String> ratings) {
        this.ratings = ratings;
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
