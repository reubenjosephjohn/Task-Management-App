package com.example.task_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity {

    private TextInputEditText phoneNumberEditText, passwordEditText;
    private Button loginButton, registerButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginUser(phoneNumber, password);
        });

        registerButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
    }

    private void loginUser(final String phoneNumber, final String password) {
        firestore.collection("users")
                .document(phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String storedPassword = document.getString("password");
                            if (storedPassword.equals(password)) {
                                showToast("Login successful");
                                // Navigate to HomeActivity
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast("Incorrect password");
                            }
                        } else {
                            showToast("User data not found");
                        }
                    } else {
                        Log.d(TAG, "Error getting user document: ", task.getException());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button press here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void checkUserData(final String phoneNumber) {
        firestore.collection("users")
                .document(phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            showToast("Login successful");
                            // Navigate to next screen
                        } else {
                            showToast("User data not found");
                        }
                    } else {
                        Log.d(TAG, "Error getting user document: ", task.getException());
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
