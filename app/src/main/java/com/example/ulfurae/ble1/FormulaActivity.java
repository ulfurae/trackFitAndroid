package com.example.ulfurae.ble1;

import entities.BMI;

/**
 * Created by heidrunh on 23.2.2017.
 */

public class FormulaActivity {

    public static BMI BMICalculate(int height, int weight) {
        BMI bmi = new BMI();
        double heightInMeters = height/(double)100;
        double BMI = weight/(heightInMeters*heightInMeters);
        double BMIIndex = Math.floor(BMI * 100) / 100;
        String idealWeight;

        if(BMIIndex < 18.5) {
            idealWeight = "Underweight";
        }
        else if(BMIIndex >= 18.5 && BMIIndex <= 24.9) {
            idealWeight = "Normal weight";
        }
        else if(BMIIndex > 24.9 && BMIIndex < 30) {
            idealWeight = "Overweight";
        }
        else {
            idealWeight = "Obesity";
        }

        bmi.setBMIIndex(BMIIndex);
        bmi.setIdealWeight(idealWeight);
        return bmi;
    }

    public boolean ProgressCal(int[] unit, int[] unitGoal) {
        return true;
    }
}