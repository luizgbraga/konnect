package com.social_network.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import com.social_network.server.HibernateUtil;
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

            Post post = new Post(parameters.get("content"), parameters.get("userId"));
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
        String searchFilterParam = request.getParameter("searchFilter");
        String userId = request.getParameter("userId");
        ArrayList<Post> posts = Post.list(minDepthParam, maxDepthParam, userId, searchFilterParam);

        String responseMessage = this.getResponseMessage(posts.toString());
        response.setStatus(201);
        response.getOutputStream().println(responseMessage);
        response.setContentType("application/json");
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        // upvote downvote
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
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("content", content);
        parameters.put("userId", userId);
        return parameters;
    }
}