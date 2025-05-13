package com.example.newspaper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(
        tableName = "emotion_table",
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
public class Emotion {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private TypeEmotion type;
    private LocalDate createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TypeEmotion getType() {
        return type;
    }

    public void setType(TypeEmotion type) {
        this.type = type;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Emotion() {
    }

    @Ignore
    public Emotion(Integer id, Integer articleId, Integer userId, TypeEmotion type, LocalDate createdAt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.type = type;
        this.createdAt = createdAt;
    }
}
