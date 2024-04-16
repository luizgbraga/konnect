package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import com.social_network.server.utils.Status;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
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

    public byte[] getUserFromId() {
        return this.getId().getUserFromId();
    }

    public byte[] getUserToId() {
        return this.getId().getUserToId();
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

    public static ArrayList<ConnectsTo> list() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo";
            Query query = session.createQuery(hql, ConnectsTo.class);
            ArrayList<ConnectsTo> connections = (ArrayList<ConnectsTo>) query.getResultList();

            transaction.commit();
            return connections;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
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
