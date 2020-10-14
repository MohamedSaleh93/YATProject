package com.mohamed.yatproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mohamed.yatproject.database.employees.Employee;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private ImageView employeeImage;
    private TextView name, position, phone, age, salary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoloyee_details);
        Intent intent = getIntent();
        Employee employee = (Employee) intent.getSerializableExtra("employee");
        employeeImage = findViewById(R.id.employeeImage);
        name = findViewById(R.id.employeeNameTV);
        position = findViewById(R.id.employeePositionTV);
        phone = findViewById(R.id.employeePhoneTV);
        age = findViewById(R.id.employeeAgeTV);
        salary = findViewById(R.id.employeeSalaryTV);

        renderEmployeeData(employee);
    }

    private void renderEmployeeData(Employee employee) {
        employeeImage.setImageURI(Uri.parse(Uri.decode(employee.imagePath)));
        name.setText(employee.name);
        position.setText(employee.position);
        phone.setText(employee.phone);
        age.setText(Integer.toString(employee.age));
        salary.setText(Double.toString(employee.salary));
    }
}
