package com.mohamed.yatproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
                Intent addEmployeeIntent = new Intent(HomeActivity.this, AddEmployeeActivity.class);
                startActivity(addEmployeeIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetEmployeeAsyncTask().execute();
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
}
