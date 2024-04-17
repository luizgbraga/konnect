package com.social_network.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
public class KnUserPK implements Serializable {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "kn_id")
    private String knId;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKnId() {
        return knId;
    }

    public void setKnId(String knId) {
        this.knId = knId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnUserPK that = (KnUserPK) o;
        return knId.equals(that.knId) && userId.equals(that.userId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId.equals(knId), knId.equals(knId));
    }
}
