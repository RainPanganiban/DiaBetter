package com.activity.diabetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        ImageButton buttonStatisticCheck = findViewById(R.id.buttonStatisticsCheck);

        logoutButton.setOnClickListener(v -> {
            // Log out the user
            auth.signOut();

            // Optional: Sign out from Google if applicable
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this,
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build());
            googleSignInClient.signOut();

            // Show a toast message
            Toast.makeText(MainMenu.this, "Logged out", Toast.LENGTH_SHORT).show();

            // Redirect to the login screen
            startActivity(new Intent(MainMenu.this, MainActivity.class));
            finish();  // Close the current activity
        });
        
        buttonStatisticCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StatisticsMenu.class));
            }
        });

        ImageButton buttonAlarmMenu = findViewById(R.id.buttonAlarmMenu);

        buttonAlarmMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AlarmMenu.class));
            }
        });
    }
}