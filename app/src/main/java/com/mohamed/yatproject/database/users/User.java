package com.mohamed.yatproject.database.users;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    public String email;

    public String password;

    @ColumnInfo(name = "image_path")
    public String imagePath;
}
