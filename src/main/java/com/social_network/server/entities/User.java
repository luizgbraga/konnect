package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import java.util.Date;

@Entity
public class User {
    @Id
    @Column(name = "id")
    private byte[] id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @OneToMany(mappedBy = "userId")
    private Collection<Post> userPosts;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User() {
    }

    public User(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        this.password = hash.toString();

        UUID uuid = UUID.randomUUID();
        byte[] bytes = new byte[16];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        this.id = bytes;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Arrays.equals(id, user.id)) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (createdAt != null ? !createdAt.equals(user.createdAt) : user.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(id);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    public Collection<Post> getPosts() {
        return userPosts;
    }

    public void addPost(Post newPost) {
        this.userPosts.add(newPost);
    }

    public static ArrayList<User> list() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();

            // Create HQL query to retrieve all users
            String hql = "FROM User";
            Query query = session.createQuery(hql, User.class);

            // Execute the query and cast the results to a List of User objects
            ArrayList<User> users = (ArrayList<User>) query.getResultList();

            transaction.commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static User getByUsername(String username) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM User WHERE username = :username";
            Query query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            User user = (User) query.getSingleResult();

            transaction.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static String login(String username, String password) {
        try {
            User user = getByUsername(username);
            return user.id.toString();
            //User.comparePassword(password, user.getPassword());

        } finally {}
    }

    private static boolean comparePassword(String userPassword, String storedHashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Assuming storedHashedPassword includes both salt and hash combined (adjust if needed)
       return true;
    }

    private static String generateJwtToken(User user) {
        // Replace 'your_secret_key' with your actual secret key
        String secretKey = "your_secret_key";

        // Use a JWT library (e.g., jjwt) for token generation
        return "token";
    }
}
