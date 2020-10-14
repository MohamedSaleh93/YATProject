package com.mohamed.yatproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mohamed.yatproject.database.EmployeesDatabaseClient;
import com.mohamed.yatproject.database.users.User;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private String email;
    private String password;

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
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError(getString(R.string.please_enter_email));
                } else if (TextUtils.isEmpty(password)) {
                    emailEditText.setError(null);
                    passwordEditText.setError(getString(R.string.please_enter_password));
                } else {
                    emailEditText.setError(null);
                    passwordEditText.setError(null);
                    checkIsUserExistOrNot();
                }
            }
        });
    }

    private void checkIsUserExistOrNot() {
        new CheckUserIsExistAsyncTask().execute();
    }

    class CheckUserIsExistAsyncTask extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... voids) {
           User savedUser = EmployeesDatabaseClient.getInstance(getApplicationContext())
                    .getEmployeesManagerDatabase()
                    .usersDAO()
                    .findByEmailAndPassword(email, password);
            return savedUser;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user == null) {
                Toast.makeText(LoginActivity.this, R.string.invalid_email_or_password,
                        Toast.LENGTH_LONG).show();
            } else {
                Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                loginIntent.putExtra("email", email);
                startActivity(loginIntent);
                finish();
            }
        }
    }
}
