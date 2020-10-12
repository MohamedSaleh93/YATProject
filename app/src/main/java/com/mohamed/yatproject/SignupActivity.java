package com.mohamed.yatproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private ImageView addUserImage;
    private EditText emailEditText,
            passwordEditText;
    private Button alreadyHaveAccountBtn;
    private Button signUpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        initializeView();
        setViewsOnClickListeners();
    }

    /**
     * To initialize the views
     */
    private void initializeView() {
        addUserImage = findViewById(R.id.addUserImage);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        alreadyHaveAccountBtn = findViewById(R.id.alreadyHaveAccountBtn);
        signUpButton = findViewById(R.id.signUpBtn);
    }

    private void setViewsOnClickListeners() {
        addUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraOrGallery();
            }
        });
        alreadyHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyHaveAccountClicked();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });
    }

    private void openCameraOrGallery() {
        // TODO add implementation for this
    }

    private void alreadyHaveAccountClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void signUpClicked() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.please_enter_password));
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.please_enter_password));
        } else if (password.length() < 8) {
            passwordEditText.setError(getString(R.string.password_should_be_greater));
        } else {
            // TODO add sign up logic
        }
    }
}
