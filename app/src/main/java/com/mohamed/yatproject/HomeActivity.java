package com.mohamed.yatproject;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohamed.yatproject.database.EmployeesDatabaseClient;
import com.mohamed.yatproject.database.employees.Employee;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView employeesRV;
    private FloatingActionButton addEmployeesBtn;
    private TextView noEmployeesError;
    private final static int ADD_EMPLOYEE_REQUEST_CODE = 100;

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        employeesRV = findViewById(R.id.emoloyeesRV);
        addEmployeesBtn = findViewById(R.id.addEmployeesBtn);
        noEmployeesError = findViewById(R.id.noEmployeesError);

        employeesRV.setLayoutManager(new LinearLayoutManager(this));

        addEmployeesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEmployeeIntent = new Intent(HomeActivity.this,
                        AddEmployeeActivity.class);
                startActivityForResult(addEmployeeIntent, ADD_EMPLOYEE_REQUEST_CODE);
            }
        });

        new GetEmployeeAsyncTask().execute();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (networkChangeReceiver == null) {
            networkChangeReceiver = new NetworkChangeReceiver();
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        registerReceiver(networkChangeReceiver, filter);

        Intent syncIntent = new Intent(this, SyncIntentService.class);
        startService(syncIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    class GetEmployeeAsyncTask extends AsyncTask<Void, List<Employee>, List<Employee>> {

        @Override
        protected List<Employee> doInBackground(Void... voids) {
            List<Employee> employeeList = EmployeesDatabaseClient.getInstance(getApplicationContext())
                    .getEmployeesManagerDatabase()
                    .employeesDAO()
                    .getAll();
            return employeeList;
        }

        @Override
        protected void onPostExecute(List<Employee> employees) {
            super.onPostExecute(employees);
            if (employees != null && employees.size() > 0) {
                noEmployeesError.setVisibility(View.GONE);
                EmployeesRVAdapter emAdapter = new EmployeesRVAdapter(employees);
                employeesRV.setAdapter(emAdapter);
            } else {
                noEmployeesError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EMPLOYEE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                new GetEmployeeAsyncTask().execute();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberme", false);
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
