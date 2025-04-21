
package com.activity.diabetter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import java.util.ArrayList;

public class StatisticsMenu extends AppCompatActivity {

    EditText ha1cInput;
    Button submitBtn;
    BarChart chart;

    ArrayList<BarEntry> ha1cEntries = new ArrayList<>();
    ArrayList<BarEntry> glucoseEntries = new ArrayList<>();
    int index = 0;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);

        ha1cInput = findViewById(R.id.ha1cInput);
        submitBtn = findViewById(R.id.submitBtn);
        chart = findViewById(R.id.chart);
        ImageButton StatsbackBtn = findViewById(R.id.StatsbackBtn);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("hba1c");



        dbRef = FirebaseDatabase.getInstance().getReference("hba1c");

// Load saved value from Firebase
        dbRef.child("latest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    double savedHa1c = snapshot.getValue(Double.class);
                    ha1cInput.setText(String.valueOf(savedHa1c));  // set in input
                    addToGraph(savedHa1c); // add to graph right away
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StatisticsMenu.this, "Failed to load HbA1c", Toast.LENGTH_SHORT).show();
            }
        });



        StatsbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });

        setupChart(); // Setup chart appearance/styling

        DatabaseReference finalDbRef = dbRef;
        submitBtn.setOnClickListener(v -> {

            String input = ha1cInput.getText().toString();
            if (!input.isEmpty()) {
                float ha1c = Float.parseFloat(input);
                if (ha1c < 6.5f || ha1c > 12f) {
                    Toast.makeText(this, "HbA1c must be between 6.5 and 12", Toast.LENGTH_SHORT).show();
                    return;
                }
                finalDbRef.child("latest").setValue(ha1c)
                        .addOnSuccessListener(unused -> Log.d("Firebase", "Saved HbA1c to Firebase"))
                        .addOnFailureListener(e -> Log.e("Firebase", "Failed to save HbA1c", e));
                float groupSpace = 0.3f;
                float barSpace = 0.05f; // space between bars in a group
                float barWidth = 0.3f;  // width of each bar
                float glucose = (28.7f * ha1c) - 46.7f;
                float xVal = index * (barWidth * 2 + groupSpace); // compute x for spacing
                ha1cEntries.add(new BarEntry(xVal, ha1c));
                glucoseEntries.add(new BarEntry(xVal + barWidth + barSpace, glucose)); // offset for second bar



                ha1cEntries.add(new BarEntry(index, ha1c));
                glucoseEntries.add(new BarEntry(index, glucose));
                index++;

                updateGraph();
            }
        });
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setEnabled(false); // Optional: hide Y axis

        chart.getAxisRight().setEnabled(false); // Disable right axis
        chart.getLegend().setEnabled(true);
    }

    private void updateGraph() {
        BarDataSet ha1cDataSet = new BarDataSet(ha1cEntries, "HbA1c");
        ha1cDataSet.setColor(Color.parseColor("#3F51B5"));
        ha1cDataSet.setValueTextColor(Color.BLACK);
        ha1cDataSet.setValueTextSize(12f);

        BarDataSet glucoseDataSet = new BarDataSet(glucoseEntries, "Avg Glucose");
        glucoseDataSet.setColor(Color.parseColor("#FF5722"));
        glucoseDataSet.setValueTextColor(Color.BLACK);
        glucoseDataSet.setValueTextSize(12f);

        BarData data = new BarData(ha1cDataSet, glucoseDataSet);
        data.setBarWidth(0.3f); // width of each bar
        chart.setData(data);

        float groupSpace = 0.3f;
        float barSpace = 0.05f;
        float groupWidth = data.getGroupWidth(groupSpace, barSpace);

        chart.getXAxis().setAxisMinimum(0f);
        chart.getXAxis().setAxisMaximum(0f + groupWidth * index);
        chart.groupBars(0f, groupSpace, barSpace); // start at x=0

        chart.invalidate();
    }

    private void addToGraph(double ha1c) {
        double glucose = (ha1c * 28.7) - 46.7;
        float xVal = index * 1.0f;

        ha1cEntries.add(new BarEntry(xVal, (float) ha1c));
        glucoseEntries.add(new BarEntry(xVal + 0.4f, (float) glucose));
        index++;

        updateGraph();
    }


}
