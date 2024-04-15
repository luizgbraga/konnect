package com.social_network.server.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;

@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private byte[] id;
    @Basic
    @Column(name = "upvotes")
    private Integer upvotes;
    @Basic
    @Column(name = "downvotes")
    private Integer downvotes;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "user_id")
    private byte[] userId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "kn_id")
    private byte[] knId;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getUserId() {
        return userId;
    }

    public void setUserId(byte[] userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getKnId() {
        return knId;
    }

    public void setKnId(byte[] knId) {
        this.knId = knId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!Arrays.equals(id, post.id)) return false;
        if (upvotes != null ? !upvotes.equals(post.upvotes) : post.upvotes != null) return false;
        if (downvotes != null ? !downvotes.equals(post.downvotes) : post.downvotes != null) return false;
        if (createdAt != null ? !createdAt.equals(post.createdAt) : post.createdAt != null) return false;
        if (!Arrays.equals(userId, post.userId)) return false;
        if (content != null ? !content.equals(post.content) : post.content != null) return false;
        if (!Arrays.equals(knId, post.knId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(id);
        result = 31 * result + (upvotes != null ? upvotes.hashCode() : 0);
        result = 31 * result + (downvotes != null ? downvotes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(userId);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(knId);
        return result;
    }
}
