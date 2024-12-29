package com.zybooks.myfitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<ExerciseItem> exerciseList;

    public ExerciseAdapter(List<ExerciseItem> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseItem exerciseItem = exerciseList.get(position);
        holder.bind(exerciseItem);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameText;
        private TextView exerciseDurationText;
        private TextView exerciseCaloriesText;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameText = itemView.findViewById(R.id.exercise_name);
            exerciseDurationText = itemView.findViewById(R.id.exercise_duration);
            exerciseCaloriesText = itemView.findViewById(R.id.exercise_calories);
        }

        public void bind(ExerciseItem exerciseItem) {
            exerciseNameText.setText(exerciseItem.getName());
            exerciseDurationText.setText("Duration: " + exerciseItem.getDuration() + " min");
            exerciseCaloriesText.setText("Calories: " + exerciseItem.getCaloriesBurned());
        }
    }
}