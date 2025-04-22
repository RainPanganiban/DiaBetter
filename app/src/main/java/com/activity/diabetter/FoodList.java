package com.activity.diabetter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

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

        buttonRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new FoodItemAdapter(recommendedMeals);
                recyclerView.setAdapter(adapter);
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
}
