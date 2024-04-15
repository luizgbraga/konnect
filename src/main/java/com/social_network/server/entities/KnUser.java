package com.social_network.server.entities;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@jakarta.persistence.Table(name = "kn_user", schema = "konnect")
public class KnUser {
    @EmbeddedId
    private KnUserPK id;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnUser knUser = (KnUser) o;

        if (!Arrays.equals(id.getUserId(), knUser.id.getUserId())) return false;
        if (!Arrays.equals(id.getKnId(), knUser.id.getKnId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(id.getUserId());
        result = 31 * result + Arrays.hashCode(id.getKnId());
        return result;
    }
}
