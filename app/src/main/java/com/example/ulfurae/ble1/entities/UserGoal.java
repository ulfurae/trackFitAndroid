package com.example.ulfurae.ble1.entities;


import java.io.Serializable;

public class UserGoal implements Serializable {

    // Declare that this attribute is the id

    private Long id;

    private Long userID;
    private int exerciseID;
    private int unit1;
    private int unit2;
    private String startDate;
    private String endDate;
    private String status;

    public UserGoal() {  }

    public UserGoal(Long userID, int exerciseID, int unit1, int unit2, String startDate, String endDate, String status) {
        this.userID = userID;
        this.exerciseID = exerciseID;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getUnit1() {
        return unit1;
    }

    public void setUnit1(int unit1) {
        this.unit1 = unit1;
    }

    public int getUnit2() {
        return unit2;
    }

    public void setUnit2(int unit2) {
        this.unit2 = unit2;
    }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
