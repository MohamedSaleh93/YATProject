package com.mohamed.yatproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

public class AddEmployeeFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee_fragment);
        AddEmployeeFragment addEmployeeFragment =
                new AddEmployeeFragment();

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager()
                        .beginTransaction();

        fragmentTransaction.add(R.id.fragment_container,
                addEmployeeFragment);
        fragmentTransaction.commit();
    }
}
