package com.l227879.fastfit;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class NutritionActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_NAME = "workout_name";
    public static final String EXTRA_SELECTED_SLOTS = "selected_slots";
    public static final String EXTRA_NUTRITION_TOTAL = "nutrition_total";

    private String workoutName;
    private String selectedSlots;

    // Item prices
    private static final double PRICE_PROTEIN_SHAKE = 8.99;
    private static final double PRICE_ENERGY_BAR = 3.49;
    private static final double PRICE_SALAD = 6.99;
    private static final double PRICE_JUICE = 4.99;

    // Quantities
    private int qtyProteinShake = 2;
    private int qtyEnergyBar = 1;
    private int qtySalad = 0;
    private int qtyJuice = 0;

    // UI elements
    private TextView tvQtyProteinShake, tvQtyEnergyBar, tvQtySalad, tvQtyJuice;
    private TextView tvTotal;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        workoutName = getIntent().getStringExtra(EXTRA_WORKOUT_NAME);
        selectedSlots = getIntent().getStringExtra(EXTRA_SELECTED_SLOTS);

        if (TextUtils.isEmpty(workoutName) || TextUtils.isEmpty(selectedSlots)) {
            Toast.makeText(this, "Missing workout data!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Find views
        tvQtyProteinShake = findViewById(R.id.tvQtyProteinShake);
        tvQtyEnergyBar = findViewById(R.id.tvQtyEnergyBar);
        tvQtySalad = findViewById(R.id.tvQtySalad);
        tvQtyJuice = findViewById(R.id.tvQtyJuice);

        tvTotal = findViewById(R.id.tvTotal);
        btnConfirm = findViewById(R.id.btnConfirm);

        Button btnMinusProteinShake = findViewById(R.id.btnMinusProteinShake);
        Button btnPlusProteinShake = findViewById(R.id.btnPlusProteinShake);

        Button btnMinusEnergyBar = findViewById(R.id.btnMinusEnergyBar);
        Button btnPlusEnergyBar = findViewById(R.id.btnPlusEnergyBar);

        Button btnMinusSalad = findViewById(R.id.btnMinusSalad);
        Button btnPlusSalad = findViewById(R.id.btnPlusSalad);

        Button btnMinusJuice = findViewById(R.id.btnMinusJuice);
        Button btnPlusJuice = findViewById(R.id.btnPlusJuice);

        // Initial display
        updateUI();

        // Protein Shake
        btnMinusProteinShake.setOnClickListener(v -> {
            if (qtyProteinShake > 0) {
                qtyProteinShake--;
                updateUI();
            }
        });
        btnPlusProteinShake.setOnClickListener(v -> {
            qtyProteinShake++;
            updateUI();
        });

        // Energy Bar
        btnMinusEnergyBar.setOnClickListener(v -> {
            if (qtyEnergyBar > 0) {
                qtyEnergyBar--;
                updateUI();
            }
        });
        btnPlusEnergyBar.setOnClickListener(v -> {
            qtyEnergyBar++;
            updateUI();
        });

        // Salad
        btnMinusSalad.setOnClickListener(v -> {
            if (qtySalad > 0) {
                qtySalad--;
                updateUI();
            }
        });
        btnPlusSalad.setOnClickListener(v -> {
            qtySalad++;
            updateUI();
        });

        // Juice
        btnMinusJuice.setOnClickListener(v -> {
            if (qtyJuice > 0) {
                qtyJuice--;
                updateUI();
            }
        });
        btnPlusJuice.setOnClickListener(v -> {
            qtyJuice++;
            updateUI();
        });

        btnConfirm.setOnClickListener(v -> {
            double total = calculateTotal();
            Intent intent = new Intent(NutritionActivity.this, WorkoutSummaryActivity.class);
            intent.putExtra(EXTRA_WORKOUT_NAME, workoutName);
            intent.putExtra(EXTRA_SELECTED_SLOTS, selectedSlots);
            intent.putExtra(EXTRA_NUTRITION_TOTAL, total);
            startActivity(intent);
        });
    }


    private void updateUI() {
        tvQtyProteinShake.setText(String.valueOf(qtyProteinShake));
        tvQtyEnergyBar.setText(String.valueOf(qtyEnergyBar));
        tvQtySalad.setText(String.valueOf(qtySalad));
        tvQtyJuice.setText(String.valueOf(qtyJuice));

        double total = calculateTotal();

        String totalText = "Total: " + String.format(Locale.US, "$%.2f", total);
        SpannableString spannableString = new SpannableString(totalText);


        spannableString.setSpan(
                new ForegroundColorSpan(0xFF111827), // black
                0,
                6, // "Total:" length
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        spannableString.setSpan(
                new ForegroundColorSpan(0xFFE10000), // red
                7,
                totalText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvTotal.setText(spannableString);
    }

    private double calculateTotal() {
        return (qtyProteinShake * PRICE_PROTEIN_SHAKE)
                + (qtyEnergyBar * PRICE_ENERGY_BAR)
                + (qtySalad * PRICE_SALAD)
                + (qtyJuice * PRICE_JUICE);
    }
}