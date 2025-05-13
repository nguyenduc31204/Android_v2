package com.example.newspaper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Builder;

@Builder
@Entity(
        tableName = "article_table",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "categoryId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("userId"), @Index("categoryId")}
)
public class Article {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private Integer viewCount;
    private Instant publishedAt;
    private Instant updatedAt;
    private Integer categoryId;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Article() {
    }

    @Ignore
    public Article(Integer id, String title, String summary, String content, String thumbnailUrl, Integer viewCount, Instant publishedAt, Instant updatedAt, Integer categoryId, Integer userId) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.viewCount = viewCount;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
        this.categoryId = categoryId;
        this.userId = userId;
    }
}
