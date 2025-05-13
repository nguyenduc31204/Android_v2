package com.example.newspaper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(
        tableName = "search_history_table",
        foreignKeys = @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
        indices = {@Index("userId")}
)
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer userId;
    private String keyword;
    private Integer searchCount;
    private LocalDate searchAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDate getSearchAt() {
        return searchAt;
    }

    public void setSearchAt(LocalDate searchAt) {
        this.searchAt = searchAt;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }

    public SearchHistory() {
    }

    @Ignore
    public SearchHistory(Integer id, Integer userId, String keyword, Integer searchCount, LocalDate searchAt) {
        this.id = id;
        this.userId = userId;
        this.keyword = keyword;
        this.searchAt = searchAt;
        this.searchCount = searchCount;
    }
}
