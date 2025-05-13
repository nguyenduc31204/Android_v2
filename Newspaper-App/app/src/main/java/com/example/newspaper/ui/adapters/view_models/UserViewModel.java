package com.example.newspaper.ui.adapters.view_models;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;

import java.sql.Date;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void registerUser(User user, UserRepository.RegistrationCallback callback) {
        repository.registerUser(user, callback);
    }

    public void login(String email, String password, UserRepository.LoginCallback callback) {
        repository.login(email, password, callback);
    }

    public LiveData<User> getUserById(long userId) {
        return repository.getUserById((int) userId);
    }

    public void updateUserProfile(long userId, String fullName, String phone,
                                  Date dob, Boolean gender, String city, String avatarUrl) {
        repository.updateUserProfile(userId, fullName, phone, dob, gender, city, avatarUrl);
    }
}