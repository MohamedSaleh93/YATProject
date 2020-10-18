package com.mohamed.yatproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mohamed.yatproject.database.EmployeesDatabaseClient;
import com.mohamed.yatproject.database.users.User;
import com.mohamed.yatproject.network.ApiClient;
import com.mohamed.yatproject.network.ILoginApi;
import com.mohamed.yatproject.network.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar loginProgress;
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
        loginProgress = findViewById(R.id.loginProgress);

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
        String response = "{\"message\":\"success\"}";
        Gson gson = new Gson();
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            String message = jsonObject.getString("message");
//            LoginResponse loginResponse = new LoginResponse();
//            loginResponse.responseMessage = message;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
        String toJson = gson.toJson(loginResponse);
 //       loginFromBackend();
        new CheckUserIsExistAsyncTask().execute();
    }

    private void loginFromBackend() {
        // Call backend
        Retrofit retrofit = ApiClient.getClient();
        ILoginApi loginApi = retrofit.create(ILoginApi.class);

        Call<LoginResponse> loginRequest = loginApi.login(email, password);
        loginProgress.setVisibility(View.VISIBLE);
        loginRequest.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    LoginResponse loginResponse = response.body();
                    loginProgress.setVisibility(View.GONE);
                    Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    loginIntent.putExtra("email", email);
                    startActivity(loginIntent);
                    finish();
                } else {
                    LoginResponse loginResponse = response.body();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login Error")
                            .setMessage(loginResponse.responseMessage)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginProgress.setVisibility(View.GONE);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Login Error")
                        .setMessage("Error in login : " + t.getMessage())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        Log.d("Test", "Hello");
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
