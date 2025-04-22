package com.activity.diabetter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Food extends Activity {

    private Button canButton, cantButton;
    private TextView food1, desc1, food2, desc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        canButton = findViewById(R.id.canButton);
        cantButton = findViewById(R.id.cantButton);

        food1 = findViewById(R.id.food1);
        desc1 = findViewById(R.id.desc1);
        food2 = findViewById(R.id.food2);
        desc2 = findViewById(R.id.desc2);

        canButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food1.setVisibility(View.VISIBLE);
                desc1.setVisibility(View.VISIBLE);
                food2.setVisibility(View.GONE);
                desc2.setVisibility(View.GONE);
            }
        });

        cantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food1.setVisibility(View.GONE);
                desc1.setVisibility(View.GONE);
                food2.setVisibility(View.VISIBLE);
                desc2.setVisibility(View.VISIBLE);
            }
        });
    }
}
