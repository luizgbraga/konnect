package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
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

        if (!id.getUserId().equals(knUser.id.getUserId())) return false;
        if (!id.getKnId().equals(knUser.id.getKnId())) return false;

        return true;
    }

    public KnUser(String userId, String knId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id = new KnUserPK(userId, knId);
    }

    public KnUser() {}

    public String getUserId() {
        return this.id.getUserId();
    }
    public String getKnId() { return this.id.getKnId(); }

    public static ArrayList<KnUser> list() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();

            // Create HQL query to retrieve all users
            String hql = "FROM KnUser";
            Query query = session.createQuery(hql, KnUser.class);

            // Execute the query and cast the results to a List of User objects
            ArrayList<KnUser> knUsers = (ArrayList<KnUser>) query.getResultList();

            transaction.commit();
            return knUsers;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public int hashCode() {
        int result = id.getUserId().hashCode();
        result = 31 * result + id.getKnId().hashCode();
        return result;
    }
}
