package com.example.newspaper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "notification_table",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Article.class,
                        parentColumns = "id",
                        childColumns = "articleId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("userId"), @Index("articleId")}
)
public class Notification {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer userId;
    private Integer articleId;
    private Boolean isRead;
    private Integer createdAt;

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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Notification() {
    }

    @Ignore
    public Notification(Integer id, Integer userId, Integer articleId, Boolean isRead, Integer createdAt) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
}
