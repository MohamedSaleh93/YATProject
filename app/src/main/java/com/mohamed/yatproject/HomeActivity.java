package com.mohamed.yatproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView employeesRV;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        employeesRV = findViewById(R.id.emoloyeesRV);

        sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
        boolean isUserChoosedRememberMe = sharedPreferences.getBoolean("rememberme", false);

        Toast.makeText(this, "User choose for remember me: " + isUserChoosedRememberMe, Toast.LENGTH_LONG).show();

        ArrayList<String> names = fakeEmployees();
        EmployeesRVAdapter emAdapter = new EmployeesRVAdapter(names);

        employeesRV.setAdapter(emAdapter);
        employeesRV.setLayoutManager(new LinearLayoutManager(this));


    }

    private ArrayList<String> fakeEmployees() {
        ArrayList<String> employeesNames = new ArrayList<>();
        employeesNames.add("Mohamed");
        employeesNames.add("Alaa");
        employeesNames.add("Kareem");
        employeesNames.add("Essam");
        employeesNames.add("Khalid");
        employeesNames.add("Mahmoud");
        employeesNames.add("Maryam");
        employeesNames.add("Menaatuallah");
        employeesNames.add("Amr");
        employeesNames.add("Atta");
        employeesNames.add("Taher");
        employeesNames.add("Yousef");
        employeesNames.add("Ziyad");
        employeesNames.add("Yat");
        employeesNames.add("Azza");
        employeesNames.add("Basant");
        employeesNames.add("Hesham");
        employeesNames.add("Gamal");
        employeesNames.add("Ismail");
        employeesNames.add("Reda");
        employeesNames.add("Taha");
        employeesNames.add("Omar");
        employeesNames.add("Shaimaa");

        return employeesNames;
    }
}
