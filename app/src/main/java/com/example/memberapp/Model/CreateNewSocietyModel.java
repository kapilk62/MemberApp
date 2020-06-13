package com.example.memberapp.Model;

public class CreateNewSocietyModel{

    String buildingId;
    String buildingType;
    String buildingName;
    String buildingWings;
    String buildingState;
    String buildingCity;
    String buildingAddress;

    public CreateNewSocietyModel(){}

    public CreateNewSocietyModel(String buildingId, String buildingType, String buildingName, String buildingWings, String buildingState, String buildingCity, String buildingAddress) {
        this.buildingId = buildingId;
        this.buildingType = buildingType;
        this.buildingName = buildingName;
        this.buildingWings = buildingWings;
        this.buildingState = buildingState;
        this.buildingCity = buildingCity;
        this.buildingAddress = buildingAddress;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingWings() {
        return buildingWings;
    }

    public String getBuildingState() {
        return buildingState;
    }

    public String getBuildingCity() {
        return buildingCity;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }
}
