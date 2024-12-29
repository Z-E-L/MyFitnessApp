package com.zybooks.myfitnessapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final Fragment caloriesFragment = new CaloriesFragment();
    private final Fragment waterFragment = new WaterFragment();
    private final Fragment exerciseFragment = new ExerciseFragment();
    private Fragment activeFragment = caloriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Add all fragments initially and show only the default fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, caloriesFragment, "CALORIES")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, waterFragment, "WATER")
                .hide(waterFragment)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, exerciseFragment, "EXERCISE")
                .hide(exerciseFragment)
                .commit();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_calories) {
                        Log.d("MainActivity", "Calories selected");
                        switchToFragment(caloriesFragment);
                        return true;
                    } else if (itemId == R.id.nav_water) {
                        Log.d("MainActivity", "Water selected");
                        switchToFragment(waterFragment);
                        return true;
                    } else if (itemId == R.id.nav_exercises) {
                        Log.d("MainActivity", "Exercises selected");
                        switchToFragment(exerciseFragment);
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    private void switchToFragment(Fragment fragment) {
        if (fragment != activeFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(activeFragment); // Hide the currently active fragment
            transaction.show(fragment); // Show the selected fragment
            transaction.commit();
            activeFragment = fragment; // Update the active fragment reference
        }
    }
}
