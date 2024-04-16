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
import java.util.List;
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

    public ConnectsTo(String userFromId, String userToId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id.setUserFromId(userFromId.getBytes());
        this.id.setUserToId(userToId.getBytes());
        this.status = Status.valueOf("pending");
    }

    public static ConnectsTo get(String userFromId, String userToId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo WHERE id.userFromId = :userFromId AND id.userToId = :userToId";
            Query query = session.createQuery(hql, ConnectsTo.class);
            query.setParameter("userFromId", userFromId);
            query.setParameter("userToId", userToId);
            ConnectsTo connection = (ConnectsTo) query.getSingleResult();

            transaction.commit();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static ArrayList<ConnectsTo> notifications(String userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo WHERE id.userToId = :userId AND status = :pending";
            Query query = session.createQuery(hql, ConnectsTo.class);
            query.setParameter("userId", userId);
            query.setParameter("pending", "pending");
            ArrayList<ConnectsTo> connection = (ArrayList<ConnectsTo>) query.getResultList();

            transaction.commit();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static List<Object[]> getUsers(String userId, String searchFilter) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            String hql = "SELECT u, c.status FROM ConnectsTo c " +
                    "INNER JOIN User u ON c.id.userToId = u.id " +
                    "WHERE c.id.userFromId = :userId AND u.username = :searchFilter";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("searchFilter", searchFilter);
            List<Object[]> results = query.getResultList();

            transaction.commit();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
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
