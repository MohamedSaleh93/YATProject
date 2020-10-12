package com.mohamed.yatproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signUpButton;
    private Switch switchBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.loginBtn);
        signUpButton = findViewById(R.id.signUpBtn);
        switchBtn = findViewById(R.id.rememberMeSwitch);

        sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();

        boolean rememberMe = sharedPreferences.getBoolean("rememberme", false);

        setViewsClickListeners();

        if (rememberMe) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void setViewsClickListeners() {
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               switchButtonChecked(isChecked);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButtonClicked();
            }
        });
    }

    private void switchButtonChecked(boolean isChecked) {
        sharedPrefEditor.putBoolean("rememberme", isChecked);
        sharedPrefEditor.apply();
        if (isChecked) {
            Toast.makeText(MainActivity.this, "I will remember you", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "I will Forget you", Toast.LENGTH_LONG).show();
        }
    }

    private void loginButtonClicked() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void signUpButtonClicked() {
        Intent signupIntent = new Intent(this, SignupActivity.class);
        startActivity(signupIntent);
    }
}
