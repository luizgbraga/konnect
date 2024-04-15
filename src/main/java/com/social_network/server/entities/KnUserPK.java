package com.social_network.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
public class KnUserPK implements Serializable {
    @Column(name = "user_id")
    private byte[] userId;
    @Column(name = "kn_id")
    private byte[] knId;
    public byte[] getUserId() {
        return userId;
    }

    public void setUserId(byte[] userId) {
        this.userId = userId;
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
        KnUserPK that = (KnUserPK) o;
        return Arrays.equals(knId, that.knId) && Arrays.equals(userId, that.userId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(userId), Arrays.hashCode(knId));
    }
}
