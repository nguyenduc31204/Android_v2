package com.example.newspaper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newspaper.models.User;

import java.sql.Date;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1")
    User getUserByIdImmediate(long userId);

    @Query("SELECT * FROM user_table WHERE id = :userId")
    LiveData<User> getUserById(long userId);

    @Query("SELECT COUNT(*) FROM user_table WHERE email = :email")
    int checkEmailExists(String email);

    @Query("UPDATE user_table SET fullName = :fullName, phone = :phone, dob = :dob, " +
            "isMale = :isMale, city = :city, avatarUrl = :avatarUrl, updatedAt = :updatedAt " +
            "WHERE id = :userId")
    void updateUserProfile(int userId, String fullName, String phone, Date dob,
                           Boolean isMale, String city, String avatarUrl, Date updatedAt);

    @Query("SELECT COUNT(*) > 0 FROM user_table WHERE email = :email")
    LiveData<Boolean> checkEmailExistsLive(String email);
}