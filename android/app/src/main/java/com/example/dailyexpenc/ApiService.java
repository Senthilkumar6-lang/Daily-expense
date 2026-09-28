package com.example.dailyexpenc;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // 1. User Login
    @POST("api/users/login")
    Call<Map<String, Object>> loginUser(@Body Map<String, String> credentials);

    // 2. Request OTP for Forgot Password
    @POST("api/users/forgot-password")
    Call<Map<String, Object>> forgotPassword(@Body Map<String, String> request);

    // 3. Verify OTP and Set New Password
    @POST("api/users/reset-password")
    Call<Map<String, Object>> resetPassword(@Body Map<String, String> request);
}