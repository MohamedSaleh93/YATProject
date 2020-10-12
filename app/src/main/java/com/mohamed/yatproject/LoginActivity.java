package com.mohamed.yatproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError(getString(R.string.please_enter_email));
                } else if (TextUtils.isEmpty(password)) {
                    emailEditText.setError(null);
                    passwordEditText.setError(getString(R.string.please_enter_password));
                } else {
                    emailEditText.setError(null);
                    passwordEditText.setError(null);

                    // TODO to add login logic here

                    Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    loginIntent.putExtra("email", email);
                    startActivity(loginIntent);
                }
            }
        });
    }
}
