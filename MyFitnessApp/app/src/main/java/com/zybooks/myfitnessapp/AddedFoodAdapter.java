package com.zybooks.myfitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AddedFoodAdapter extends RecyclerView.Adapter<AddedFoodAdapter.AddedFoodViewHolder> {

    private List<FoodItem> addedFoodList;
    private OnFoodRemoveListener removeListener;

    public AddedFoodAdapter(List<FoodItem> addedFoodList, OnFoodRemoveListener removeListener) {
        this.addedFoodList = addedFoodList;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public AddedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new AddedFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedFoodViewHolder holder, int position) {
        FoodItem foodItem = addedFoodList.get(position);
        holder.nameTextView.setText(foodItem.getName());
        holder.caloriesTextView.setText(foodItem.getCalories() + " kcal");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeListener.onFoodRemove(foodItem);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return addedFoodList.size();
    }

    public static class AddedFoodViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView caloriesTextView;

        public AddedFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(android.R.id.text1);
            caloriesTextView = itemView.findViewById(android.R.id.text2);
        }
    }

    public interface OnFoodRemoveListener {
        void onFoodRemove(FoodItem foodItem);
    }
}