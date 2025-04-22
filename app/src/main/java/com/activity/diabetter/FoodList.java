package com.activity.diabetter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {
    private List<FoodItem> recommendedMeals;
    private List<FoodItem> mealsToAvoid;
    private RecyclerView recyclerView;
    private FoodItemAdapter adapter;
    private EditText ha1cInput; // Declare EditText for user input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        ha1cInput = findViewById(R.id.hba1cInput); // Initialize the EditText
        Button buttonRecommended = findViewById(R.id.buttonRecommended);
        Button buttonAvoid = findViewById(R.id.buttonAvoid);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        recommendedMeals = new ArrayList<>();
        recommendedMeals.add(new FoodItem("Oatmeal", "Oats", "Low"));
        recommendedMeals.add(new FoodItem("Grilled Chicken", "Chicken", "Low"));
        recommendedMeals.add(new FoodItem("Quinoa Salad", "Quinoa", "Low"));
        recommendedMeals.add(new FoodItem("Broccoli", "Vegetable", "Low"));
        recommendedMeals.add(new FoodItem("Greek Yogurt", "Yogurt", "Medium"));
        recommendedMeals.add(new FoodItem("Almonds", "Nuts", "Low"));
        recommendedMeals.add(new FoodItem("Berries", "Fruit", "Low"));
        recommendedMeals.add(new FoodItem("Salmon", "Fish", "Low"));
        recommendedMeals.add(new FoodItem("Spinach", "Vegetable", "Low"));
        recommendedMeals.add(new FoodItem("Brown Rice", "Rice", "Medium"));
        // ... (add other recommended meals)

        mealsToAvoid = new ArrayList<>();
        mealsToAvoid.add(new FoodItem("Candy", "Sugar", "High"));
        mealsToAvoid.add(new FoodItem("Soda", "Sugar", "High"));
        mealsToAvoid.add(new FoodItem("White Bread", "Wheat", "Medium"));
        mealsToAvoid.add(new FoodItem("Pastries", "Flour", "High"));
        mealsToAvoid.add(new FoodItem("Ice Cream", "Dairy", "High"));
        mealsToAvoid.add(new FoodItem("Chips", "Potato", "Medium"));
        mealsToAvoid.add(new FoodItem("Processed Meats", "Meat", "Medium"));
        mealsToAvoid.add(new FoodItem("Fried Foods", "Oil", "High"));
        mealsToAvoid.add(new FoodItem("Sweetened Yogurt", "Yogurt", "High"));
        mealsToAvoid.add(new FoodItem("Cereal", "Grain", "Medium"));
        // ... (add other meals to avoid)

        buttonRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecommendedMeals();
            }
        });

        buttonAvoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new FoodItemAdapter(mealsToAvoid);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void showRecommendedMeals() {
        String input = ha1cInput.getText().toString().trim(); // Get user input

        if (!input.isEmpty()) {
            try {
                double ha1c = Double.parseDouble(input); // Parse the input to double

                // Determine which meals to show based on the ha1c value
                if (ha1c <- 6.7) {
                    adapter = new FoodItemAdapter(recommendedMeals);
                } else if (ha1c >= 7 && ha1c < 8) {
                    adapter = new FoodItemAdapter(recommendedMeals); // Customize as needed
                } else if (ha1c >= 10) {
                    adapter = new FoodItemAdapter(mealsToAvoid);
                }

                // Set the adapter to the RecyclerView
                recyclerView.setAdapter(adapter);

            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                Toast.makeText(this, "Invalid HbA1c value. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Prompt the user to enter a value if the input is empty
            Toast.makeText(this, "Please enter a HbA1c value.", Toast.LENGTH_SHORT).show();
        }
    }
}

