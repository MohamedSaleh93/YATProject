package com.mohamed.yatproject.database;

import android.content.Context;

import androidx.room.Room;

public class EmployeesDatabaseClient {

    private Context mContex;
    private EmployeesManagerDatabase employeesManagerDatabase;
    private static EmployeesDatabaseClient employeesDatabaseClient;
    private final static String DATABASE_NAME = "employee_manager";

    private EmployeesDatabaseClient(Context context) {
        this.mContex = context;

        employeesManagerDatabase = Room.databaseBuilder(mContex,
                EmployeesManagerDatabase.class, DATABASE_NAME).build();

    }

    public static synchronized EmployeesDatabaseClient getInstance(Context context) {
        if (employeesDatabaseClient == null) {
            employeesDatabaseClient = new EmployeesDatabaseClient(context);
        }
        return employeesDatabaseClient;
    }

    public EmployeesManagerDatabase getEmployeesManagerDatabase() {
        return employeesManagerDatabase;
    }
}
