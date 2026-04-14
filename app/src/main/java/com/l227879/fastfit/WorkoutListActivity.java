package com.l227879.fastfit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutListActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_NAME = "workout_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        Button btnBookLegPress = findViewById(R.id.btnBookLegPress);
        Button btnBookLatPulldown = findViewById(R.id.btnBookLatPulldown);
        Button btnBookChestPress = findViewById(R.id.btnBookChestPress);

        Button btnWatchLegPress = findViewById(R.id.btnWatchLegPress);
        Button btnWatchLatPulldown = findViewById(R.id.btnWatchLatPulldown);
        Button btnWatchChestPress = findViewById(R.id.btnWatchChestPress);

        btnBookLegPress.setOnClickListener(v -> openSlotBooking("Leg Press"));
        btnBookLatPulldown.setOnClickListener(v -> openSlotBooking("Lat Pulldown"));
        btnBookChestPress.setOnClickListener(v -> openSlotBooking("Chest Press"));

        btnWatchLegPress.setOnClickListener(v -> openYoutube("https://www.youtube.com/results?search_query=leg+press+machine+tutorial"));
        btnWatchLatPulldown.setOnClickListener(v -> openYoutube("https://www.youtube.com/results?search_query=lat+pulldown+machine+tutorial"));
        btnWatchChestPress.setOnClickListener(v -> openYoutube("https://www.youtube.com/results?search_query=chest+press+machine+tutorial"));
    }

    private void openSlotBooking(String workoutName) {
        if (TextUtils.isEmpty(workoutName)) {
            Toast.makeText(this, "Workout name missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(WorkoutListActivity.this, SlotBookingActivity.class);
        intent.putExtra(EXTRA_WORKOUT_NAME, workoutName);
        startActivity(intent);
    }

    private void openYoutube(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "No app found to open tutorial.", Toast.LENGTH_SHORT).show();
        }
    }
}