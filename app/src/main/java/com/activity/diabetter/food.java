package com.activity.diabetter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class food extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // 1) Grab your buttons + TextViews
        Button btnMeals = findViewById(R.id.btnRecommend);
        Button btnAvoid = findViewById(R.id.btnAvoid);

        TextView name1 = findViewById(R.id.foodName1);
        TextView info1 = findViewById(R.id.foodInfo1);
        TextView name2 = findViewById(R.id.foodName2);
        TextView info2 = findViewById(R.id.foodInfo2);
        TextView name3 = findViewById(R.id.foodName3);
        TextView info3 = findViewById(R.id.foodInfo3);

        // 2) Your test glucose level and threshold
        final int glucoseLevel = 7;
        final int threshold    = 7;

        // 3) Meals for You
        btnMeals.setOnClickListener(v -> {
            if (glucoseLevel <= threshold) {
                // Low/normal glucose → quick‑sugar helpers
                name1.setText("Orange Juice");
                info1.setText("Quick sugar boost; good for low glucose.");
                name2.setText("Grapes");
                info2.setText("Easy to eat fruit; moderate sugar.");
                name3.setText("Honey");
                info3.setText("Natural sugar; small serving.");
            } else {
                // High glucose → slow‑release meals
                name1.setText("Brown Rice");
                info1.setText("Complex carbs; good for steady glucose.");
                name2.setText("Adobo");
                info2.setText("High protein; eat moderately.");
                name3.setText("Boiled Egg");
                info3.setText("Lean protein; safe choice.");
            }
        });

        // 4) Foods to Avoid
        btnAvoid.setOnClickListener(v -> {
            if (glucoseLevel <= threshold) {
                // Low glucose: you probably don’t *need* avoid-list,
                // but let’s show high-sugar treats anyway
                name1.setText("Donut");
                info1.setText("High sugar; may spike then crash.");
                name2.setText("Soda");
                info2.setText("Sugary drink; not recommended.");
                name3.setText("Cake");
                info3.setText("Too sweet; avoid if you can.");
            } else {
                // High glucose: avoid these
                name1.setText("Donut");
                info1.setText("High sugar; spikes glucose quickly.");
                name2.setText("Soda");
                info2.setText("Sugary drink; not recommended.");
                name3.setText("Cake");
                info3.setText("Too sweet; avoid when glucose is high.");
            }
        });

        btnMeals.performClick();
    }
}