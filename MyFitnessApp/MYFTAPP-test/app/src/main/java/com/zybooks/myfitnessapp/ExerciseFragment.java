package com.zybooks.myfitnessapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseFragment extends Fragment {

    private ExerciseViewModel viewModel;
    private TextView totalCaloriesBurnedText;
    private ExerciseAdapter exerciseAdapter;
    private Map<String, Integer> predefinedExercises;
    private List<ExerciseItem> filteredExercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        // Initialize views
        SearchView searchExerciseView = view.findViewById(R.id.search_exercise);
        RecyclerView exerciseRecyclerView = view.findViewById(R.id.exercise_recycler_view);
        totalCaloriesBurnedText = view.findViewById(R.id.total_calories_burned);
        Button addExerciseButton = view.findViewById(R.id.add_exercise_button);

        // Initialize RecyclerView and Adapter
        filteredExercises = new ArrayList<>(viewModel.getExerciseList());
        exerciseAdapter = new ExerciseAdapter(filteredExercises);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseRecyclerView.setAdapter(exerciseAdapter);

        // Set up predefined exercises
        predefinedExercises = new HashMap<>();
        populatePredefinedExercises();

        // Set up search functionality
        searchExerciseView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterExerciseList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterExerciseList(newText);
                return true;
            }
        });

        // Set up add exercise button click listener
        addExerciseButton.setOnClickListener(v -> showAddExerciseDialog());

        updateTotalCaloriesBurned();
        return view;
    }

    private void showAddExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Exercise");

        // Convert predefinedExercises to a list of names
        List<String> exerciseNames = new ArrayList<>(predefinedExercises.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exerciseNames);

        // Set up ListView inside dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_food_list, null);
        ListView exerciseListView = dialogView.findViewById(R.id.food_list_view);
        exerciseListView.setAdapter(adapter);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Item click listener to add selected exercise
        exerciseListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedExercise = exerciseNames.get(position);
            int caloriesPerMinute = predefinedExercises.get(selectedExercise);

            showDurationDialog(selectedExercise, caloriesPerMinute);
            dialog.dismiss();
        });

        dialog.show();
    }



    private void showDurationDialog(String exerciseName, int caloriesPerMinute) {
        // Inflate the custom duration input dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_exercise, null);
        EditText exerciseNameInput = dialogView.findViewById(R.id.exercise_name);
        EditText durationInput = dialogView.findViewById(R.id.exercise_duration);

        // Pre-fill exercise name and make it read-only
        exerciseNameInput.setText(exerciseName);
        exerciseNameInput.setFocusable(false);
        exerciseNameInput.setClickable(false);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Enter Duration")
                .setView(dialogView)
                .setPositiveButton("Add", (dialogInterface, which) -> {
                    String durationText = durationInput.getText().toString().trim();

                    if (TextUtils.isEmpty(durationText)) {
                        Toast.makeText(getContext(), "Please enter duration.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Add the exercise and update RecyclerView
                    int duration = Integer.parseInt(durationText);
                    viewModel.addExercise(new ExerciseItem(exerciseName, duration, caloriesPerMinute * duration));
                    updateTotalCaloriesBurned();
                    // Refresh the adapter data
                    filteredExercises.clear();
                    filteredExercises.addAll(viewModel.getExerciseList());
                    exerciseAdapter.notifyDataSetChanged();

                    // Clear focus from the search bar
                    clearSearchBarFocus();
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    private void clearSearchBarFocus() {
        SearchView searchExerciseView = getView().findViewById(R.id.search_exercise);
        if (searchExerciseView != null) {
            searchExerciseView.clearFocus();
        }
    }






    private void filterExerciseList(String query) {
        filteredExercises.clear();
        for (ExerciseItem exercise : viewModel.getExerciseList()) {
            if (exercise.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredExercises.add(exercise);
            }
        }
        exerciseAdapter.notifyDataSetChanged();
    }

    private void updateTotalCaloriesBurned() {
        totalCaloriesBurnedText.setText("Total Calories Burned: " + viewModel.getTotalCaloriesBurned());
    }

    private void populatePredefinedExercises() {
        predefinedExercises.put("Running", 10);          // Calories burned per minute
        predefinedExercises.put("Cycling", 8);
        predefinedExercises.put("Swimming", 12);
        predefinedExercises.put("Yoga", 5);
        predefinedExercises.put("Walking", 4);
        predefinedExercises.put("Weightlifting", 6);
        predefinedExercises.put("Dancing", 7);

        // Additional exercises
        predefinedExercises.put("Jumping Jacks", 9);
        predefinedExercises.put("Push-Ups", 8);
        predefinedExercises.put("Pull-Ups", 10);
        predefinedExercises.put("Squats", 7);
        predefinedExercises.put("Plank", 6);
        predefinedExercises.put("Rowing", 9);
        predefinedExercises.put("Hiking", 6);
        predefinedExercises.put("Elliptical Machine", 7);
        predefinedExercises.put("Stair Climbing", 11);
        predefinedExercises.put("Pilates", 5);
        predefinedExercises.put("Aerobics", 8);
        predefinedExercises.put("Kickboxing", 10);
        predefinedExercises.put("Tennis", 9);
        predefinedExercises.put("Basketball", 9);
        predefinedExercises.put("Soccer", 10);
        predefinedExercises.put("Skateboarding", 6);
        predefinedExercises.put("Skiing", 12);
        predefinedExercises.put("Rollerblading", 9);
        predefinedExercises.put("Jump Rope", 13);
        predefinedExercises.put("Climbing", 10);
        predefinedExercises.put("Golf (Walking, Carrying Clubs)", 5);
        predefinedExercises.put("Ice Skating", 8);
        predefinedExercises.put("Surfing", 6);
        predefinedExercises.put("Martial Arts", 11);
    }

}
