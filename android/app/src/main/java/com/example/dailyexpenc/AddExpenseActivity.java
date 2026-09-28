package com.example.dailyexpenc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etAmount, etDescription;
    Button btnSaveExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        btnSaveExpense.setOnClickListener(v -> {

            String amount = etAmount.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (amount.isEmpty()) {
                etAmount.setError("Enter amount");
                return;
            }

            Toast.makeText(
                    AddExpenseActivity.this,
                    "Expense Saved: ₹" + amount,
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}