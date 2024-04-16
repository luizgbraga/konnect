package com.social_network.server.entities;

import com.social_network.server.utils.Status;
import jakarta.persistence.*;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;

@Entity
@jakarta.persistence.Table(name = "connects_to", schema = "konnect")
public class ConnectsTo {
    @EmbeddedId
    private ConnectsToPK id;

    @Basic
    @Column(name = "status")
    private Status status;

    public ConnectsToPK getId() {
        return id;
    }

    public void setId(ConnectsToPK id) {
        this.id = id;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ConnectsTo(byte[] userFromId, byte[] userToId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id.setUserFromId(userFromId);
        this.id.setUserToId(userToId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectsTo that = (ConnectsTo) o;

        if (!Arrays.equals(id.getUserFromId(), that.id.getUserFromId())) return false;
        if (!Arrays.equals(id.getUserToId(), that.id.getUserToId())) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(id.getUserFromId());
        result = 31 * result + Arrays.hashCode(id.getUserToId());
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
