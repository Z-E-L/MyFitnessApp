package com.zybooks.myfitnessapp;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ExerciseViewModel extends ViewModel {
    private List<ExerciseItem> exerciseList = new ArrayList<>();
    private int totalCaloriesBurned = 0;

    public List<ExerciseItem> getExerciseList() {
        return exerciseList;
    }

    public void addExercise(ExerciseItem exercise) {
        exerciseList.add(exercise);
        totalCaloriesBurned += exercise.getCaloriesBurned();
    }



    public void removeExercise(ExerciseItem exercise) {
        exerciseList.remove(exercise);
        totalCaloriesBurned -= exercise.getCaloriesBurned();
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }
}