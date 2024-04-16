package com.social_network.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import com.social_network.server.HibernateUtil;
import com.social_network.server.entities.ConnectsTo;
import com.social_network.server.entities.Post;
import com.social_network.server.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
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

@WebServlet(name = "notification-api", value = "/api/notification")
public class NotificationAPI extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        ArrayList<ConnectsTo> connections = ConnectsTo.notifications(userId);

        String responseMessage = this.getResponseMessage(connections.toString());
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