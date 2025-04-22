package com.activity.diabetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Foods extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods); // This should match the XML file name



        ImageButton foodback = findViewById(R.id.backFoodButton);
        foodback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Foods.this, MainMenu.class); // Replace 'Login.class' with your actual target
                startActivity(intent);
                finish(); // Optional
            }
        });

    }



}
