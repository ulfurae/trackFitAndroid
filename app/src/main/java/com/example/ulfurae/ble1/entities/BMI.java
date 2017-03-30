package com.example.ulfurae.ble1.entities;

// A class that calculates the BMI based on height and weight of a user
public class BMI {

    private double BMIIndex;
    private String idealWeight;

    public BMI() { }

    public BMI(double BMIIndex, String idealWeight) {
        this.BMIIndex = BMIIndex;
        this.idealWeight = idealWeight;
    }

    public double getBMIIndex() {
        return BMIIndex;
    }

    public void setBMIIndex(double BMIIndex) {
        this.BMIIndex = BMIIndex;
    }

    public String getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(String idealWeight) {
        this.idealWeight = idealWeight;
    }

}