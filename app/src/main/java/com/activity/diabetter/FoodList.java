package com.activity.diabetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    private EditText hba1cInput;
    private Button submitBtn;
    private Button buttonRecommended;
    private Button buttonAvoid;
    private RecyclerView recyclerView;
    private FoodItemAdapter foodAdapter;
    private List<FoodItem> foodItems;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);;

        imageButton = findViewById(R.id.back2);
        hba1cInput = findViewById(R.id.hba1cInput);
        submitBtn = findViewById(R.id.submitBtn);
        buttonRecommended = findViewById(R.id.buttonRecommended);
        buttonAvoid = findViewById(R.id.buttonAvoid);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodItems = new ArrayList<>();
        foodAdapter = new FoodItemAdapter(foodItems);
        recyclerView.setAdapter(foodAdapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });

        submitBtn.setOnClickListener(v -> {
            String input = hba1cInput.getText().toString().trim();

            if (!input.isEmpty()) {
                try {
                    float hba1c = Float.parseFloat(input);
                    // Clear previous food items
                    foodItems.clear();
                    foodAdapter.notifyDataSetChanged();

                    // Call the method to show recommended foods
                    showRecommendedFoods(hba1c);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a HbA1c value", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRecommended.setOnClickListener(v -> {
            String input = hba1cInput.getText().toString().trim();
            if (!input.isEmpty()) {
                float hba1c = Float.parseFloat(input);
                showRecommendedFoods(hba1c);
            } else {
                Toast.makeText(this, "Please enter a HbA1c value", Toast.LENGTH_SHORT).show();
            }
        });

        buttonAvoid.setOnClickListener(v -> {
            String input = hba1cInput.getText().toString().trim();
            if (!input.isEmpty()) {
                float hba1c = Float.parseFloat(input);
                showFoodsToAvoid(hba1c);
            } else {
                Toast.makeText(this, "Please enter a HbA1c value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRecommendedFoods(float hba1c) {
        foodItems.clear(); // Clear previous items
        if (hba1c >= 6.5f && hba1c <= 7.0f) {
            foodItems.add(new FoodItem("Whole grains (brown rice)", "Grain", "Low"));
            foodItems.add(new FoodItem("Green leafy vegetables", "Vegetable", "Low"));
            foodItems.add(new FoodItem("Lean protein (boiled egg)", "Protein", "Low"));
            foodItems.add(new FoodItem("Low-sugar fruits (berries)", "Fruit", "Low"));
            foodItems.add(new FoodItem("Unsweetened green tea", "Beverage", "Low"));
        } else if (hba1c > 7.0f && hba1c <= 8.5f) {
            foodItems.add(new FoodItem("Low-carb vegetables (broccoli)", "Vegetable", "Low"));
            foodItems.add(new FoodItem("Legumes (lentils)", "Legume", "Medium"));
            foodItems.add(new FoodItem("Tofu", "Protein", "Low"));
            foodItems.add(new FoodItem("Complex carbs (¼ cup cooked grains)", "Grain", "Medium"));
            foodItems.add(new FoodItem("Water", "Beverage", "Low"));
        } else if (hba1c > 8.5f) {
            foodItems.add(new FoodItem("Leafy greens", "Vegetable", "Low"));
            foodItems.add(new FoodItem("High-fat, low-carb foods (avocado)", "Fat", "Low"));
            foodItems.add(new FoodItem("Protein-rich foods (eggs)", "Protein", "Low"));
            foodItems.add(new FoodItem("No more than 20–30g carbs per day (very low-carb)", "Carbohydrate", "Very Low"));
            foodItems.add(new FoodItem("Hydrate with water only", "Beverage", "Low"));
        }

        foodAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
    }

    private void showFoodsToAvoid(float hba1c) {
        foodItems.clear(); // Clear previous items
        if (hba1c >= 6.5f && hba1c <= 7.0f) {
            foodItems.add(new FoodItem("White rice", "Grain", "High"));
            foodItems.add(new FoodItem("White bread", "Grain", "High"));
            foodItems.add(new FoodItem("Sugary drinks", "Beverage", "High"));
            foodItems.add(new FoodItem("High-sugar snacks (candies, cakes)", "Snack", "High"));
        } else if (hba1c > 7.0f && hba1c <= 8.5f) {
            foodItems.add(new FoodItem("Fruit juices (even natural)", "Beverage", "High"));
            foodItems.add(new FoodItem("Sweetened dairy products", "Dairy", "High"));
            foodItems.add(new FoodItem("Instant noodles", "Snack", "High"));
            foodItems.add(new FoodItem("Processed snacks", "Snack", "High"));
            foodItems.add(new FoodItem("Sweet sauces (ketchup, barbecue)", "Condiment", "High"));
        } else if (hba1c > 8.5f) {
            foodItems.add(new FoodItem("All fruits except minimal berries", "Fruit", "High"));
            foodItems.add(new FoodItem("All sugar-sweetened products", "Snack", "High"));
            foodItems.add(new FoodItem("Breads, noodles, rice, sweet potatoes, corn", "Grain", "High"));
            foodItems.add(new FoodItem("Milk tea, soda, flavored drinks", "Beverage", "High"));
            foodItems.add(new FoodItem("Processed food", "Food", "High"));
        }

        foodAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
    }

}