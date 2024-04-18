package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Entity
public class Kn {
    @Id
    @jakarta.persistence.Column(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Kn() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] nouns = {"Eagle", "Heart", "Rock", "Forest", "Sea", "Light", "Sky", "Mountain", "River", "Star", "Path", "Wind", "Ocean", "Temple", "Shadow", "Valley", "Thunder", "Moon", "Fire", "Snow"};
        String[] adjectives = {"Bright", "Silent", "Fierce", "Sacred", "Mysterious", "Majestic", "Wild", "Serene", "Radiant", "Lost", "Icy", "Harmonious", "Epic", "Legendary", "Divine", "Glistening", "Majestic", "Dazzling", "Enchanted", "Burning"};

        Random rand1 = new Random();
        Random rand2 = new Random();

        String randomNoun = nouns[rand1.nextInt(nouns.length)];
        String randomAdjective = adjectives[rand2.nextInt(adjectives.length)];

        this.name = randomAdjective + " " + randomNoun;

        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public static ArrayList<Kn> list() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();

            // Create HQL query to retrieve all users
            String hql = "FROM Kn";
            Query query = session.createQuery(hql, Kn.class);
            // Execute the query and cast the results to a List of User objects
            ArrayList<Kn> kn = (ArrayList<Kn>) query.getResultList();
            transaction.commit();
            return kn;
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

        Kn kn = (Kn) o;

        if (!id.equals(kn.id)) return false;
        if (name != null ? !name.equals(kn.name) : kn.name != null) return false;
        if (createdAt != null ? !createdAt.equals(kn.createdAt) : kn.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
