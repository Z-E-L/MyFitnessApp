package com.zybooks.myfitnessapp;

public class ExerciseItem {

    private String name;
    private int duration; // in minutes
    private int caloriesBurned;

    public ExerciseItem(String name, int duration, int caloriesBurned) {
        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
}