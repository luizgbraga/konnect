package com.social_network.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
public class ConnectsToPK implements Serializable {
    @Column(name = "user_from_id")
    private byte[] userFromId;
    @Column(name = "user_to_id")
    private byte[] userToId;

    public ConnectsToPK(){}

    public ConnectsToPK(byte[] userFromId, byte[] userToId) {
        this.userFromId = userFromId;
        this.userToId = userToId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectsToPK that = (ConnectsToPK) o;
        return Arrays.equals(userFromId, that.userFromId) && Arrays.equals(userToId, that.userToId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(userFromId), Arrays.hashCode(userToId));
    }

    public byte[] getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(byte[] userFromId) {
        this.userFromId = userFromId;
    }

    public byte[] getUserToId() {
        return userToId;
    }

    public void setUserToId(byte[] userToId) {
        this.userToId = userToId;
    }
}
