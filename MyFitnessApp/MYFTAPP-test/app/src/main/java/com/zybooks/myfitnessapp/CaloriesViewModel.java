package com.zybooks.myfitnessapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class CaloriesViewModel extends ViewModel {
    private static final String KEY_CONSUMED_FOODS = "key_consumed_foods";
    private static final String KEY_TOTAL_CALORIES = "key_total_calories";

    private SavedStateHandle savedStateHandle;

    public CaloriesViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<List<FoodItem>> getConsumedFoodListLiveData() {
        if (!savedStateHandle.contains(KEY_CONSUMED_FOODS)) {
            savedStateHandle.set(KEY_CONSUMED_FOODS, new ArrayList<FoodItem>());
        }
        return savedStateHandle.getLiveData(KEY_CONSUMED_FOODS);
    }

    public List<FoodItem> getConsumedFoodList() {
        List<FoodItem> foodList = savedStateHandle.get(KEY_CONSUMED_FOODS);
        return foodList != null ? foodList : new ArrayList<>();
    }

    public void setConsumedFoodList(List<FoodItem> foodList) {
        savedStateHandle.set(KEY_CONSUMED_FOODS, foodList);

    }

    public LiveData<Integer> getTotalCaloriesLiveData() {
        if (!savedStateHandle.contains(KEY_TOTAL_CALORIES)) {
            savedStateHandle.set(KEY_TOTAL_CALORIES, 0);
        }
        return savedStateHandle.getLiveData(KEY_TOTAL_CALORIES);
    }

    public int getTotalCalories() {
        return savedStateHandle.get(KEY_TOTAL_CALORIES) == null ? 0 : savedStateHandle.get(KEY_TOTAL_CALORIES);
    }

    public void setTotalCalories(int totalCalories) {
        savedStateHandle.set(KEY_TOTAL_CALORIES, totalCalories);
    }

    public void addFood(String foodName, int calories) {
        List<FoodItem> currentFoods = getConsumedFoodList();
        currentFoods.add(new FoodItem(foodName, calories));
        setConsumedFoodList(currentFoods);

        int newCalories = getTotalCalories() + calories;
        setTotalCalories(newCalories);
    }

    public void removeFood(String foodName, int calories) {
        List<FoodItem> currentFoods = getConsumedFoodList();
        currentFoods.remove(new FoodItem(foodName, calories));
        setConsumedFoodList(currentFoods);

        int newCalories = Math.max(0, getTotalCalories() - calories);
        setTotalCalories(newCalories);
    }
}