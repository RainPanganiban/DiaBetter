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

    private EditText hba1cInput;
    private Button submitBtn;
    private Button buttonRecommended;
    private Button buttonAvoid;
    private RecyclerView recyclerView;
    private FoodItemAdapter foodAdapter;
    private List<FoodItem> foodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

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

        submitBtn.setOnClickListener(v -> {
            String input = hba1cInput.getText().toString().trim();

            if (!input.isEmpty()) {
                try {
                    float hba1c = Float.parseFloat(input);

                    // Validate HbA1c value
                    if (hba1c < 6.5f || hba1c > 12.0f) {
                        Toast.makeText(this, "Invalid input. Please enter a value between 6.5 and 12.0.", Toast.LENGTH_SHORT).show();
                        return; // Exit the method if the input is invalid
                    }

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
            foodItems.add(new FoodItem(
                    "Food: Tinola",
                    "Main Ingredient: Chicken and Green Papaya",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Ginisang Ampalaya",
                    "Main Ingredient: Ampalaya and Egg",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Inihaw na Isda",
                    "Main Ingredient: Fish (Bangus/Tilapia)",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Brown Rice",
                    "Main Ingredient: Unpolished Rice",
                    "Sugar Level: Moderate (watch portion)"
            ));

            foodItems.add(new FoodItem(
                    "Food: Lumpiang Sariwa",
                    "Main Ingredient: Fresh Vegetables",
                    "Sugar Level: Low (without sweet sauce)"
            ));
        } else if (hba1c > 7.0f && hba1c <= 8.5f) {
            foodItems.add(new FoodItem(
                    "Food: Utan Bisaya",
                    "Main Ingredient: Mixed Native Vegetables",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Tokwa’t Baboy",
                    "Main Ingredient: Tofu and Pork (Tofu-heavy)",
                    "Sugar Level: Low (no sweet sauce)"
            ));

            foodItems.add(new FoodItem(
                    "Food: Tortang Talong",
                    "Main Ingredient: Eggplant and Egg",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Sinigang na Isda",
                    "Main Ingredient: Fish and Sour Vegetables",
                    "Sugar Level: Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Cauliflower Fried Rice",
                    "Main Ingredient: Cauliflower",
                    "Sugar Level: Very Low"
            ));
        } else if (hba1c > 8.5f) {
            foodItems.add(new FoodItem(
                    "Food: Laing",
                    "Main Ingredient: Gabi Leaves with Coconut Milk",
                    "Sugar Level: Very Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Paksiw na Isda",
                    "Main Ingredient: Fish in Vinegar",
                    "Sugar Level: Very Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Adobong Sitaw",
                    "Main Ingredient: String Beans and Garlic",
                    "Sugar Level: Very Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Egg with Tomatoes and Onions",
                    "Main Ingredient: Eggs and Vegetables",
                    "Sugar Level: Very Low"
            ));

            foodItems.add(new FoodItem(
                    "Food: Avocado Slices with Vinegar",
                    "Main Ingredient: Avocado",
                    "Sugar Level: Very Low"
            ));
        }

        foodAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
    }

    private void showFoodsToAvoid(float hba1c) {
        foodItems.clear(); // Clear previous items
        if (hba1c >= 6.5f && hba1c <= 7.0f) {
            foodItems.add(new FoodItem(
                    "Food: White Rice",
                    "Main Ingredient: Grain",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: White Bread",
                    "Main Ingredient: Grain",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Fruit Juices (Even Natural)",
                    "Main Ingredient: Beverage",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Arroz Caldo",
                    "Main Ingredient: Glutinous Rice",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Kakanin (Puto, Bibingka, Suman)",
                    "Main Ingredient: Glutinous Rice and Sugar",
                    "Sugar Level: High"
            ));
        } else if (hba1c > 7.0f && hba1c <= 8.5f) {
            foodItems.add(new FoodItem(
                    "Food: High-Sugar Snacks (Candies, Cakes)",
                    "Main Ingredient: Snack",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: Instant Noodles",
                    "Main Ingredient: Snack",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: Processed Snacks",
                    "Main Ingredient: Snack",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Sweetened Dairy Products",
                    "Main Ingredient: Dairy",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Sweet Sauces (Ketchup, Barbecue)",
                    "Main Ingredient: Condiment",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Pancit Canton (Instant or Sauced)",
                    "Main Ingredient: Noodles with Sauce",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Ensaymada",
                    "Main Ingredient: Bread with Butter and Sugar",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Leche Flan",
                    "Main Ingredient: Condensed Milk and Sugar",
                    "Sugar Level: High"
            ));
        } else if (hba1c > 8.5f) {
            foodItems.add(new FoodItem(
                    "Food: Breads, Noodles, Rice, Sweet Potatoes, Corn",
                    "Main Ingredient: Grain",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Sugary Drinks",
                    "Main Ingredient: Beverage",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: Milk Tea, Soda, Flavored Drinks",
                    "Main Ingredient: Beverage",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: All Fruits Except Minimal Berries",
                    "Main Ingredient: Fruit",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: All Sugar-Sweetened Products",
                    "Main Ingredient: Snack",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Processed Food",
                    "Main Ingredient: Mixed/Packaged",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Halo-Halo",
                    "Main Ingredient: Mixed Sweet Ingredients and Sugar",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: Ube Halaya",
                    "Main Ingredient: Purple Yam and Condensed Milk",
                    "Sugar Level: High"
            ));

            foodItems.add(new FoodItem(
                    "Food: Turon (Banana Lumpia with Sugar)",
                    "Main Ingredient: Banana and Caramelized Sugar",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Pan de Coco",
                    "Main Ingredient: Coconut Filling and Sweet Bread",
                    "Sugar Level: High"
            ));
            foodItems.add(new FoodItem(
                    "Food: Ice Cream (Commercial Brands)",
                    "Main Ingredient: Sweetened Dairy",
                    "Sugar Level: High"
            ));
        }

        foodAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
    }

    }