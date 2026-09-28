package com.example.dailyexpenc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etForgotEmail, etOtp, etNewPassword;
    private Button btnSendOtp, btnResetPassword;
    private LinearLayout layoutResetSection;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etForgotEmail = findViewById(R.id.etForgotEmail);
        etOtp = findViewById(R.id.etOtp);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        layoutResetSection = findViewById(R.id.layoutResetSection);

        // Retrofit API client
        apiService = ApiClient.getClient().create(ApiService.class);

        // 1. Send OTP Button Click
        btnSendOtp.setOnClickListener(v -> {
            String email = etForgotEmail.getText().toString().trim();
            if (email.isEmpty()) {
                etForgotEmail.setError("Enter your email");
                return;
            }

            Map<String, String> req = new HashMap<>();
            req.put("email", email);

            apiService.forgotPassword(req).enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "OTP Sent! Check backend console / email", Toast.LENGTH_SHORT).show();
                        layoutResetSection.setVisibility(View.VISIBLE);
                        etForgotEmail.setEnabled(false);
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email not registered!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(ForgotPasswordActivity.this, "Connection Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // 2. Reset Password Button Click
        btnResetPassword.setOnClickListener(v -> {
            String email = etForgotEmail.getText().toString().trim();
            String otp = etOtp.getText().toString().trim();
            String newPass = etNewPassword.getText().toString().trim();

            if (otp.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Enter OTP and New Password", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> req = new HashMap<>();
            req.put("email", email);
            req.put("otp", otp);
            req.put("newPassword", newPass);

            apiService.resetPassword(req).enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully!", Toast.LENGTH_LONG).show();
                        finish(); // Returns back to Login screen
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(ForgotPasswordActivity.this, "Connection Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}