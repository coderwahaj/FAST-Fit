package com.l227879.fastfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashSet;
import java.util.Set;

public class SlotBookingActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_NAME = "workout_name";
    public static final String EXTRA_SELECTED_SLOTS = "selected_slots";

    private TextView tvWorkoutName;
    private TextView tvSelectedCount;

    private Button btnMorning;
    private Button btnAfternoon;
    private Button btnEvening;

    private Button btnProceedNutrition;
    private Button btnConfirmWorkout;

    private final Set<String> selectedSlots = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_booking);

        tvWorkoutName = findViewById(R.id.tvWorkoutName);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);

        btnMorning = findViewById(R.id.btnMorning);
        btnAfternoon = findViewById(R.id.btnAfternoon);
        btnEvening = findViewById(R.id.btnEvening);

        btnProceedNutrition = findViewById(R.id.btnProceedNutrition);
        btnConfirmWorkout = findViewById(R.id.btnConfirmWorkout);

        String workoutName = getIntent().getStringExtra(EXTRA_WORKOUT_NAME);
        if (TextUtils.isEmpty(workoutName)) {
            Toast.makeText(this, "Workout name not received!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        tvWorkoutName.setText(workoutName);

        //  Initialize Morning as selected by default
        selectedSlots.add("Morning");
        btnMorning.setBackgroundResource(R.drawable.btn_slot_selected);
        btnMorning.setTextColor(0xFFFFFFFF);

        // Initial UI
        updateSelectedCount();
        updateProceedButtonState();

        btnMorning.setOnClickListener(v -> toggleSlot("Morning", btnMorning));
        btnAfternoon.setOnClickListener(v -> toggleSlot("Afternoon", btnAfternoon));
        btnEvening.setOnClickListener(v -> toggleSlot("Evening", btnEvening));

        btnProceedNutrition.setOnClickListener(v -> {
            if (selectedSlots.isEmpty()) {
                Toast.makeText(this, "Select at least one slot!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SlotBookingActivity.this, NutritionActivity.class);
            intent.putExtra(EXTRA_WORKOUT_NAME, workoutName);
            intent.putExtra(EXTRA_SELECTED_SLOTS, getSelectedSlotsCsv());
            startActivity(intent);
        });

        btnConfirmWorkout.setOnClickListener(v -> {
            if (selectedSlots.isEmpty()) {
                Toast.makeText(this, "Select at least one slot!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(SlotBookingActivity.this, WorkoutSummaryActivity.class);
            intent.putExtra(EXTRA_WORKOUT_NAME, workoutName);
            intent.putExtra(EXTRA_SELECTED_SLOTS, getSelectedSlotsCsv());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  Re-enable buttons when user comes back from Nutrition screen
        setSlotButtonsEnabled(true);
        updateProceedButtonState();
    }

    private void toggleSlot(String slot, Button button) {
        if (selectedSlots.contains(slot)) {
            // Unselect: white background + dark text
            selectedSlots.remove(slot);
            button.setBackgroundResource(R.drawable.btn_slot_unselected);
            button.setTextColor(0xFF111827);
        } else {
            // Select: red background + white text
            selectedSlots.add(slot);
            button.setBackgroundResource(R.drawable.btn_slot_selected);
            button.setTextColor(0xFFFFFFFF);
        }

        updateSelectedCount();
        updateProceedButtonState();
    }

    private void updateSelectedCount() {
        tvSelectedCount.setText("Selected Slots: " + selectedSlots.size());
    }

    private void updateProceedButtonState() {
        btnProceedNutrition.setEnabled(!selectedSlots.isEmpty());
        btnProceedNutrition.setAlpha(selectedSlots.isEmpty() ? 0.5f : 1.0f);
    }

    private void setSlotButtonsEnabled(boolean enabled) {
        btnMorning.setEnabled(enabled);
        btnAfternoon.setEnabled(enabled);
        btnEvening.setEnabled(enabled);

        float alpha = enabled ? 1.0f : 0.6f;
        btnMorning.setAlpha(alpha);
        btnAfternoon.setAlpha(alpha);
        btnEvening.setAlpha(alpha);
    }

    private String getSelectedSlotsCsv() {
        StringBuilder sb = new StringBuilder();
        for (String s : selectedSlots) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(s);
        }
        return sb.toString();
    }
}