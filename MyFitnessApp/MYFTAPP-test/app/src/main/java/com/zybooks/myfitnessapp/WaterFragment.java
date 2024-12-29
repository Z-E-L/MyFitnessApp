package com.zybooks.myfitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WaterFragment extends Fragment {

    private static final String PREFS_NAME = "water_tracker_prefs";
    private static final String KEY_WATER_CONSUMED = "water_consumed";
    private static final String KEY_WATER_GOAL = "water_goal";

    private int waterGoal = 2000; // Default daily goal in mL
    private int waterConsumed;
    private TextView currentWaterText;
    private ProgressBar waterProgressBar;
    private TextView dailyWaterGoalText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);

        dailyWaterGoalText = view.findViewById(R.id.daily_water_goal);
        currentWaterText = view.findViewById(R.id.current_water);
        waterProgressBar = view.findViewById(R.id.water_progress_bar);
        Button addWaterButton = view.findViewById(R.id.add_water_button);
        Button resetButton = view.findViewById(R.id.reset_water_button);
        Button setGoalButton = view.findViewById(R.id.set_goal_button);
        EditText goalInput = view.findViewById(R.id.goal_input);

        // Load saved data
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        waterGoal = prefs.getInt(KEY_WATER_GOAL, 2000); // Default to 2000 mL
        waterConsumed = prefs.getInt(KEY_WATER_CONSUMED, 0);

        updateWaterDisplay();

        // Add water button listener
        addWaterButton.setOnClickListener(v -> addWater(250)); // Default add 250 mL

        // Reset button listener
        resetButton.setOnClickListener(v -> resetWaterTracker());

        // Set custom goal listener
        setGoalButton.setOnClickListener(v -> {
            String input = goalInput.getText().toString();
            if (!input.isEmpty()) {
                waterGoal = Integer.parseInt(input);
                prefs.edit().putInt(KEY_WATER_GOAL, waterGoal).apply();
                updateWaterDisplay();
            }
        });

        return view;
    }

    private void addWater(int amount) {
        if (waterConsumed < waterGoal) {
            waterConsumed += amount;
            if (waterConsumed > waterGoal) {
                waterConsumed = waterGoal; // Prevent overflow
            }
            saveWaterConsumed();
            updateWaterDisplay();
        }
    }

    private void resetWaterTracker() {
        waterConsumed = 0;
        saveWaterConsumed();
        updateWaterDisplay();
    }

    private void saveWaterConsumed() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_WATER_CONSUMED, waterConsumed).apply();
    }

    private void updateWaterDisplay() {
        dailyWaterGoalText.setText("Daily Water Goal: " + waterGoal + " mL");
        currentWaterText.setText("Water Consumed: " + waterConsumed + " mL");
        waterProgressBar.setMax(waterGoal);
        waterProgressBar.setProgress(waterConsumed);
    }
}
