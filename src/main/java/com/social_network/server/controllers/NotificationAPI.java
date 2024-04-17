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
        ArrayList<User> users = User.list("");
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("[");
        for (ConnectsTo connection : connections) {
            String username = "Not found";
            for (User user : users) {
                if (connection.getUserFromId().equals(user.getId())) {
                    username = user.getUsername();
                    break; // Found the user, no need to continue iterating
                }
            }
            responseBuilder.append("{")
                    .append("\"id\": \"").append(connection.getUserFromId()).append("\", ")
                    .append("\"username\": \"").append(username).append("\"")
                    .append("}, ");
        }
        if (!connections.isEmpty()) {
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
            String userFromId = request.getParameter("userFromId");
            String userToId = request.getParameter("userToId");
            System.out.println(userFromId);
            System.out.println(userToId);

            ConnectsTo connection = ConnectsTo.get(userFromId, userToId);
            System.out.println(connection);
            connection.setStatus("active");
            session.merge(connection); // Use merge to update detached entity
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

    private HashMap<String, String> getUpdateConnectionParameters(HttpServletRequest request) throws IOException {
        StringBuilder jsonDataBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonDataBuilder.append(line);
        }
        String jsonData = jsonDataBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        String userFromId = jsonObject.getString("userFromId");
        String userToId = jsonObject.getString("userToId");
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("userFromId", userFromId);
        parameters.put("userToId", userToId);
        return parameters;
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