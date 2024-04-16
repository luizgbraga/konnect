package com.social_network.server.entities;

import jakarta.persistence.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
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
    private String userId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "kn_id")
    private String knId;

    public Post() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKnId() {
        return knId;
    }

    public void setKnId(String knId) {
        this.knId = knId;
    }

    public Post(String content) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.content = content;

        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!id.equals(post.id)) return false;
        if (upvotes != null ? !upvotes.equals(post.upvotes) : post.upvotes != null) return false;
        if (downvotes != null ? !downvotes.equals(post.downvotes) : post.downvotes != null) return false;
        if (createdAt != null ? !createdAt.equals(post.createdAt) : post.createdAt != null) return false;
        if (!userId.equals(post.userId)) return false;
        if (content != null ? !content.equals(post.content) : post.content != null) return false;
        if (!knId.equals(post.knId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (upvotes != null ? upvotes.hashCode() : 0);
        result = 31 * result + (downvotes != null ? downvotes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + userId.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + knId.hashCode();
        return result;
    }
}
