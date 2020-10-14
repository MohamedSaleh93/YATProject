package com.mohamed.yatproject.database.employees;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeesDAO {

    @Query("SELECT * FROM employees")
    List<Employee> getAll();

    @Insert
    void insertEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);
}
