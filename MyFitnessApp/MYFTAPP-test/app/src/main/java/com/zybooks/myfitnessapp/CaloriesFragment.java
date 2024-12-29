package com.zybooks.myfitnessapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaloriesFragment extends Fragment {

    private CaloriesViewModel viewModel;
    private FoodAdapter consumedAdapter;
    private TextView totalCaloriesText;
    private ProgressBar calorieProgressBar;

    private Map<String, Integer> predefinedFoods; // Predefined foods list

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(CaloriesViewModel.class);

        Button addFoodButton = view.findViewById(R.id.add_food_button);
        RecyclerView consumedRecyclerView = view.findViewById(R.id.consumed_food_recycler_view);
        totalCaloriesText = view.findViewById(R.id.total_calories_text);
        calorieProgressBar = view.findViewById(R.id.calorie_progress_bar);

        consumedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize consumed food list and adapter
        consumedAdapter = new FoodAdapter(viewModel.getConsumedFoodList(), this::removeCalories);
        consumedRecyclerView.setAdapter(consumedAdapter);

        // Restore state if savedInstanceState is not null
       /* if (savedInstanceState != null) {
            int savedCalories = savedInstanceState.getInt("totalCalories", 0);
            ArrayList<FoodItem> savedFoods = savedInstanceState.getParcelableArrayList("consumedFoods");
            viewModel.setTotalCalories(savedCalories);
            viewModel.setConsumedFoodList(savedFoods != null ? savedFoods : new ArrayList<>());
        }*/

        // Observe ViewModel
        viewModel.getTotalCaloriesLiveData().observe(getViewLifecycleOwner(), calories -> {
            totalCaloriesText.setText("Total Calories: " + calories);
            calorieProgressBar.setProgress(calories);
        });

        viewModel.getConsumedFoodListLiveData().observe(getViewLifecycleOwner(), consumedAdapter::updateFoodList);


        // Set up predefined food items
        predefinedFoods = new HashMap<>();
        populatePredefinedFoods();

        // Set up add food button click listener
        addFoodButton.setOnClickListener(v -> showAddFoodDialog());

        // Display the initial total calories
        updateTotalCaloriesDisplay();

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save current state to the Bundle
        outState.putInt("totalCalories", viewModel.getTotalCalories());
        outState.putParcelableArrayList("consumedFoods", new ArrayList<>(viewModel.getConsumedFoodList()));
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Food");

        // Convert predefinedFoods to a list of names and prepare adapter
        List<String> foodNames = new ArrayList<>(predefinedFoods.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, foodNames);

        // Set up ListView inside dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_food_list, null);
        ListView foodListView = dialogView.findViewById(R.id.food_list_view);
        foodListView.setAdapter(adapter);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Item click listener to add selected food
        foodListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFood = foodNames.get(position);
            int calories = predefinedFoods.get(selectedFood);
            addCalories(new FoodItem(selectedFood, calories));
            dialog.dismiss(); // Dismiss the dialog after selection
        });

        dialog.show();
    }

    private void addCalories(FoodItem foodItem) {
        // Delegate state management to the ViewModel
        viewModel.addFood(foodItem.getName(), foodItem.getCalories());
        consumedAdapter.notifyDataSetChanged();
        updateTotalCaloriesDisplay();
    }

    private void removeCalories(FoodItem foodItem) {
        viewModel.removeFood(foodItem.getName(), foodItem.getCalories());

        consumedAdapter.notifyDataSetChanged();
        updateTotalCaloriesDisplay();
    }

    private void updateTotalCaloriesDisplay() {
        int totalCalories = viewModel.getTotalCalories();
        totalCaloriesText.setText("Total Calories: " + totalCalories);
        calorieProgressBar.setProgress(totalCalories);

        if (totalCalories >= 2000) { // Assume 2000 is the calorie goal
            totalCaloriesText.setText("Goal reached! Total: " + totalCalories);
        }
    }

    private void populatePredefinedFoods() {
        predefinedFoods.put("Oatmeal", 150);
        predefinedFoods.put("Eggs", 80);
        predefinedFoods.put("Grilled Chicken Salad", 300);
        predefinedFoods.put("Steak", 400);
        predefinedFoods.put("Mixed Veggies", 150);
        predefinedFoods.put("Apple", 95);
        predefinedFoods.put("Banana", 105);
        predefinedFoods.put("Greek Yogurt", 100);
        predefinedFoods.put("Avocado Toast", 250);
        predefinedFoods.put("Smoothie", 200);
        predefinedFoods.put("Chicken Breast", 165);
        predefinedFoods.put("Rice (1 cup)", 205);
        predefinedFoods.put("Pasta", 220);
        predefinedFoods.put("Burger", 540);
        predefinedFoods.put("Pizza Slice", 285);
        predefinedFoods.put("Salmon Fillet", 200);
        predefinedFoods.put("Protein Shake", 150);
        predefinedFoods.put("Granola Bar", 100);
        predefinedFoods.put("Almonds (1 oz)", 160);
        predefinedFoods.put("Peanut Butter (1 tbsp)", 95);
        predefinedFoods.put("Cereal", 120);
        predefinedFoods.put("Milk (1 cup)", 150);
        predefinedFoods.put("Orange Juice (1 cup)", 110);
        predefinedFoods.put("Brown Rice (1 cup)", 215);
        predefinedFoods.put("Tofu", 70);
        predefinedFoods.put("Lentil Soup", 180);
        predefinedFoods.put("Caesar Salad", 310);
        predefinedFoods.put("French Fries", 365);
        predefinedFoods.put("Chocolate Bar", 210);
        predefinedFoods.put("Ice Cream", 140);
        predefinedFoods.put("Fruit Salad", 120);
        predefinedFoods.put("Turkey Sandwich", 300);
        predefinedFoods.put("Vegetable Stir Fry", 170);
        predefinedFoods.put("Sushi Roll", 200);
        predefinedFoods.put("Shrimp Cocktail", 120);
        predefinedFoods.put("Pancakes", 350);
        predefinedFoods.put("Cheese Slice", 90);
        predefinedFoods.put("Energy Drink", 110);
        predefinedFoods.put("Tea (unsweetened)", 0);
        predefinedFoods.put("Coffee (black)", 0);
        predefinedFoods.put("Latte", 190);
        predefinedFoods.put("Muffin", 400);
        predefinedFoods.put("Bagel with Cream Cheese", 350);
        predefinedFoods.put("Bacon", 42);
        predefinedFoods.put("Sausage", 180);
        predefinedFoods.put("Spaghetti", 220);
        predefinedFoods.put("Roasted Potatoes", 150);
        predefinedFoods.put("Green Salad with Dressing", 80);
    }
}