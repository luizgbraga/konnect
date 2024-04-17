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
    private String userFromId;
    @Column(name = "user_to_id")
    private String userToId;

    public ConnectsToPK(){}

    public ConnectsToPK(String userFromId, String userToId) {
        this.userFromId = userFromId;
        this.userToId = userToId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectsToPK that = (ConnectsToPK) o;
        return userFromId.equals(that.userFromId) && userToId.equals(that.userToId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userFromId.hashCode(), userToId.hashCode());
    }

    public String getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(String userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserToId() {
        return userToId;
    }

    public void setUserToId(String userToId) {
        this.userToId = userToId;
    }
}
