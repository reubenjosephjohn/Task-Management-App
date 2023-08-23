package com.example.task_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DatePicker dobDatePicker;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        dobDatePicker = findViewById(R.id.datePicker);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        final String name = ((TextInputEditText) findViewById(R.id.nameEditText)).getText().toString();
        final String email = ((TextInputEditText) findViewById(R.id.emailEditText)).getText().toString();
        final String phoneNumber = ((TextInputEditText) findViewById(R.id.phoneNumberEditText)).getText().toString();
        final String password = ((TextInputEditText) findViewById(R.id.passwordEditText)).getText().toString();

        // Get selected date from DatePicker
        int year = dobDatePicker.getYear();
        int month = dobDatePicker.getMonth();
        int day = dobDatePicker.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Intent sendOTPIntent = new Intent(RegisterActivity.this, SendOTPActivity.class);
        sendOTPIntent.putExtra("name", name);
        sendOTPIntent.putExtra("email", email);
        sendOTPIntent.putExtra("phoneNumber", phoneNumber);
        sendOTPIntent.putExtra("password", password);
        sendOTPIntent.putExtra("dateOfBirth", calendar.getTime());
        startActivity(sendOTPIntent);


    }
}
