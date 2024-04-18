package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import com.social_network.server.utils.Graph;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
public class Post {
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "upvotes")
    private Integer upvotes;
    @Basic
    @Column(name = "downvotes")
    private Integer downvotes;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "kn_id")
    private String knId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKnId() {
        if (this.knId == null) {
            return "null";
        } else {
            return this.knId;
        }
    }

    public void setKnId(String knId) {
        this.knId = knId;
    }

    public Post() {}

    public Post(String content, String userId, String knId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.content = content;
        this.userId = userId;
        if (!knId.equals("null")) {
            this.knId = knId;
        }

        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.downvotes = 0;
        this.upvotes = 0;
    }

    public static Post get(String postId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM Post WHERE id = :id";
            Query query = session.createQuery(hql, Post.class);
            query.setParameter("id", postId);
            Post post = (Post) query.getResultList().get(0);

            transaction.commit();
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }
    public static ArrayList<Post> list(Integer minDepth, Integer maxDepth, String userId) {
        ArrayList<ConnectsTo> connections = ConnectsTo.list();
        Graph graph = new Graph(connections);
        List<Graph.NodeDepthPair> users = graph.findNodesWithinDepthRange(userId, minDepth, maxDepth);
        List<String> userIds = new ArrayList<>();

        for (Graph.NodeDepthPair user : users) {
            userIds.add(new String(user.getNodeId()));
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            System.out.println(userIds);
            transaction.begin();
            String hql = "FROM Post WHERE userId IN (:userIds)";
            Query query = session.createQuery(hql, Post.class);
            query.setParameter("userIds", userIds);
            ArrayList<Post> posts = (ArrayList<Post>) query.getResultList();

            transaction.commit();
            return posts;
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

        Post post = (Post) o;

        if (!id.equals(post.id)) return false;
        if (upvotes != null ? !upvotes.equals(post.upvotes) : post.upvotes != null) return false;
        if (downvotes != null ? !downvotes.equals(post.downvotes) : post.downvotes != null) return false;
        if (createdAt != null ? !createdAt.equals(post.createdAt) : post.createdAt != null) return false;
        if (!userId.equals(post.userId)) return false;
        if (content != null ? !content.equals(post.content) : post.content != null) return false;
        if (!knId.equals(post.knId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (upvotes != null ? upvotes.hashCode() : 0);
        result = 31 * result + (downvotes != null ? downvotes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + userId.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (knId != null ? knId.hashCode() : 0);
        return result;
    }
}
