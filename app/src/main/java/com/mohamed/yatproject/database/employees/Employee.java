package com.mohamed.yatproject.database.employees;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "employees")
public class Employee implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;

    public String position;

    public int age;

    public String phone;

    public double salary;

    @ColumnInfo(name = "image_path")
    public String imagePath;

}
