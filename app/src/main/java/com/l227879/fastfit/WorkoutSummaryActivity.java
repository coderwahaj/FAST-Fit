package com.l227879.fastfit;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class WorkoutSummaryActivity extends AppCompatActivity {

    private static final String HARDCODED_PHONE = "+1234567890"; // Change this to your test number


    private String workoutName;
    private String selectedSlots;
    private double nutritionTotal;

    private TextView tvWorkoutName;
    private TextView tvSelectedSlots;
    private TextView tvNutritionTotal;
    private TextView tvTotalPrice;

    private Button btnSendPlan;
    private Button btnSendSms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_summary);

        workoutName = getIntent().getStringExtra(NutritionActivity.EXTRA_WORKOUT_NAME);
        selectedSlots = getIntent().getStringExtra(SlotBookingActivity.EXTRA_SELECTED_SLOTS);
        nutritionTotal = getIntent().getDoubleExtra(NutritionActivity.EXTRA_NUTRITION_TOTAL, 0.0);

        tvWorkoutName = findViewById(R.id.tvWorkoutName);
        tvSelectedSlots = findViewById(R.id.tvSelectedSlots);
        tvNutritionTotal = findViewById(R.id.tvNutritionTotal);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        // Use CardView instead of Button
        CardView btnSendPlan = findViewById(R.id.btnSendPlan);
        CardView btnSendSms = findViewById(R.id.btnSendSms);

        // Validation
        if (TextUtils.isEmpty(workoutName) || TextUtils.isEmpty(selectedSlots)) {
            Toast.makeText(this, "Missing data!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display data
        tvWorkoutName.setText(workoutName);
        tvSelectedSlots.setText(selectedSlots);
        tvNutritionTotal.setText(String.format(Locale.US, "$%.2f", nutritionTotal));
        tvTotalPrice.setText(String.format(Locale.US, "$%.2f", nutritionTotal));

        btnSendPlan.setOnClickListener(v -> sendPlanViaShare());
        btnSendSms.setOnClickListener(v -> sendSmsConfirmation());
    }

    private void sendPlanViaShare() {
        String message = "Workout Plan\n\n"
                + "Workout: " + workoutName + "\n"
                + "Slots: " + selectedSlots + "\n"
                + "Nutrition Total: $" + String.format(Locale.US, "%.2f", nutritionTotal) + "\n"
                + "Total Price: $" + String.format(Locale.US, "%.2f", nutritionTotal);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(shareIntent, "Share Workout Plan via"));
    }

    private static final int SMS_PERMISSION_CODE = 101;

    private void sendSmsConfirmation() {
        if (TextUtils.isEmpty(workoutName) || TextUtils.isEmpty(selectedSlots)) {
            Toast.makeText(this, "Cannot send SMS: data missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_CODE);
        } else {
            // Permission already granted, send SMS
            sendSms();
        }
    }

    private void sendSms() {
        String message = "Workout Confirmed!\n"
                + "Workout: " + workoutName + "\n"
                + "Slots: " + selectedSlots + "\n"
                + "Total: $" + String.format(Locale.US, "%.2f", nutritionTotal);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(HARDCODED_PHONE, null, message, null, null);
            Toast.makeText(this, "SMS sent to " + HARDCODED_PHONE, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}