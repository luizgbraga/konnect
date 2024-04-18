package com.social_network.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.social_network.server.HibernateUtil;
import com.social_network.server.entities.ConnectsTo;
import com.social_network.server.entities.Post;
import com.social_network.server.entities.User;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.annotation.WebServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.json.JSONObject;

@WebServlet(name = "post-api", value = "/api/post")
public class PostAPI extends HttpServlet {
    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            HashMap<String, String> parameters = this.getPostParameters(request);

            Post post = new Post(parameters.get("content"), parameters.get("userId"), parameters.get("knId"));
            System.out.println(post.getId());
            System.out.println(post.getContent());
            System.out.println(post.getUserId());
            session.persist(post);
            transaction.commit();
            String responseMessage = this.getResponseMessage("Post created successfully");
            response.setStatus(201);
            response.getOutputStream().println(responseMessage);
            response.setContentType("application/json");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer minDepthParam = Integer.parseInt(request.getParameter("minDepth"));
        Integer maxDepthParam = Integer.parseInt(request.getParameter("maxDepth"));
        String userId = request.getParameter("userId");
        String knId = request.getParameter("groupId");
        ArrayList<Post> posts = Post.list(minDepthParam, maxDepthParam, userId);
        if (posts == null) {
            String responseMessage = this.getResponseMessage("[]");
            response.setStatus(201);
            response.getOutputStream().println(responseMessage);
            response.setContentType("application/json");
            return;
        }
        ArrayList<User> users = User.list("");
        List<Post> filteredPosts = posts.stream()
                .filter(post -> {
                    if (!knId.equals("null")) {
                        return post.getKnId().equals(knId);
                    } else {
                        return post.getKnId().equals("null");
                    }
                })
                .collect(Collectors.toList());
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("[");
        for (Post post : filteredPosts) {
            String username = "Not found";
            for (User user : users) {
                if (post.getUserId().equals(user.getId())) {
                    username = user.getUsername();
                    break; // Found the user, no need to continue iterating
                }
            }
            responseBuilder.append("{")
                    .append("\"id\": \"").append(post.getId()).append("\", ")
                    .append("\"username\": \"").append(username).append("\", ")
                    .append("\"content\": \"").append(post.getContent()).append("\", ")
                    .append("\"userId\": \"").append(post.getUserId()).append("\", ")
                    .append("\"upvotes\": \"").append(post.getUpvotes()).append("\", ")
                    .append("\"downvotes\": \"").append(post.getDownvotes()).append("\" ")
                    .append("}, ");
        }
        if (!filteredPosts.isEmpty()) {
            responseBuilder.delete(responseBuilder.length() - 2, responseBuilder.length());
        }

        responseBuilder.append("]"); // End of JSON array
        String responseMessage = this.getResponseMessage(responseBuilder.toString());
        response.setStatus(201);
        response.getOutputStream().println(responseMessage);
        response.setContentType("application/json");
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String postId = request.getParameter("postId");
            String vote = request.getParameter("vote");
            System.out.println(postId);
            System.out.println(vote);

            Post post = Post.get(postId);
            System.out.println(post);
            if (vote.equals("upvote")) {
                post.setUpvotes(post.getUpvotes() + 1);
            }
            if (vote.equals("downvote")) {
                post.setDownvotes(post.getDownvotes() + 1);
            }
            session.merge(post); // Use merge to update detached entity
            if (!transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
                transaction.rollback();
                throw new Exception();
            }
            transaction.commit();
            String responseMessage = this.getResponseMessage("Connection updated successfully");
            response.setStatus(200);
            response.getOutputStream().println(responseMessage);
            response.setContentType("application/json");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    public void destroy() {
    }

    private String getResponseMessage(String message) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("message", message);
        return responseObject.toString();
    }

    private HashMap<String, String> getPostParameters(HttpServletRequest request) throws IOException {
        StringBuilder jsonDataBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonDataBuilder.append(line);
        }
        String jsonData = jsonDataBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        String content = jsonObject.getString("content");
        String userId = jsonObject.getString("userId");
        String groupId = jsonObject.getString("groupId");
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("content", content);
        parameters.put("userId", userId);
        parameters.put("knId", groupId);
        return parameters;
    }
}