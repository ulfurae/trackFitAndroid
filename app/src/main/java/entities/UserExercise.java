package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserExercise {

    // Declare that this attribute is the id

    private Long id;

    private Long userGoalID;
    private Long userID;
    private Long exerciseID;

    private int unit1;
    private int unit2;
    private Date date;

    public UserExercise() { }

    public UserExercise(Long userGoalID, Long userID, Long exerciseID, int unit1, int unit2, Date date) {
        this.userGoalID = userGoalID;
        this.userID = userID;
        this.exerciseID = exerciseID;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserGoalID() {
        return userGoalID;
    }

    public void setUserGoalID(long userGoalID) {
        this.userGoalID = userGoalID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getUnit1() {
        return unit1;
    }
    public int getUnit2() {
        return unit2;
    }

    public void setUnit1(int unit1) {
        this.unit1 = unit1;
    }
    public void setUnit2(int unit2) {
        this.unit2 = unit2;
    }

    /*private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public String getDate() {
        return format.format(date);
    }*/
    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }
}

