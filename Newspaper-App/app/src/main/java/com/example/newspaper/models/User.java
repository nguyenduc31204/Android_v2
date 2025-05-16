package com.example.newspaper.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;


@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String username;
    public String email;
    public String passwordHash;
    public String avatarUrl;
    public String fullName;
    public String phone;
    public Date dob;
    public Boolean isMale;
    public String city;
    public String role;
    public Date createdAt;
    public Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Ignore
    public User(String username, String email, String passwordHash, String Role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = Role;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }


    @Ignore
    public User() {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.passwordHash = passwordHash;
//        this.avatarUrl = avatarUrl;
//        this.fullName = fullName;
//        this.phone = phone;
//        this.dob = dob;
//        this.isMale = isMale;
//        this.city = city;
//        this.role = role;
//        this.createdAt = new Date();
//        this.updatedAt = new Date();
    }

    public User(Integer id, String username, String email, String passwordHash,
                String avatarUrl, String fullName, String phone, Date dob,
                Boolean isMale, String city, String role, Date createdAt, Date updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.phone = phone;
        this.dob = dob;
        this.isMale = isMale;
        this.city = city;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    public String getGenderAsString() {
        return isMale == null ? "Không xác định" : (isMale ? "Nam" : "Nữ");
    }

    public void updateBasicInfo(String fullName, String phone, Date dob, Boolean isMale, String city) {
        this.fullName = fullName;
        this.phone = phone;
        this.dob = dob;
        this.isMale = isMale;
        this.city = city;
        this.updatedAt = new Date();
    }
}
