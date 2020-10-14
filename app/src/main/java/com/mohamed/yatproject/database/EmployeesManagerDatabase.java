package com.mohamed.yatproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mohamed.yatproject.database.employees.Employee;
import com.mohamed.yatproject.database.employees.EmployeesDAO;
import com.mohamed.yatproject.database.users.User;
import com.mohamed.yatproject.database.users.UsersDAO;

@Database(entities = {User.class, Employee.class}, version = 1)
public abstract class EmployeesManagerDatabase extends RoomDatabase {

    public abstract UsersDAO usersDAO();

    public abstract EmployeesDAO employeesDAO();

}
