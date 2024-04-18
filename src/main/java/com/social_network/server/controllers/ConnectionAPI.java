package com.social_network.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.social_network.server.HibernateUtil;
import com.social_network.server.entities.*;
import com.social_network.server.utils.Status;
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

@WebServlet(name = "connection-api", value = "/api/connection")
public class ConnectionAPI extends HttpServlet {
    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            HashMap<String, String> parameters = this.getCreateConnectionParameters(request);

            String userFromId = parameters.get("userFromId");
            String userToId = parameters.get("userToId");
            ConnectsTo connection = new ConnectsTo(userFromId, userToId);
            session.persist(connection);
            Map<String, List<List<String>>> result = ConnectsTo.checkGroups();
            List<List<String>> mustCreate = result.get("mustCreate");
            List<String> mustDelete = result.get("mustDelete").get(0);
            System.out.println(mustCreate);
            System.out.println(mustDelete);
            if (!mustDelete.isEmpty()) {
                session.createQuery("DELETE FROM KnUser WHERE id.userId IN :ids")
                        .setParameterList("ids", mustDelete)
                        .executeUpdate();
            }
            if (!mustCreate.isEmpty()) {
                for (List<String> kn : mustCreate) {
                    Kn group = new Kn();
                    System.out.println(group);
                    session.persist(group);
                    for (String userId : kn) {
                        KnUser belongs = new KnUser(userId, group.getId());
                        System.out.println(belongs);
                        session.persist(belongs);
                    }
                }
            }
            transaction.commit();
            String responseMessage = this.getResponseMessage("Connection created successfully");
            response.setStatus(201);
            response.getOutputStream().println(responseMessage);
            response.setContentType("application/json");

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String searchFilter = request.getParameter("searchFilter");
        ArrayList<User> users = User.list(searchFilter);
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("[");
        for (User user : users) {
            // Constructing JSON-like representation for each user
            responseBuilder.append("{")
                    .append("\"id\": \"").append(user.getId()).append("\", ")
                    .append("\"username\": \"").append(user.getUsername()).append("\"")
                    .append("}, ");
        }
        if (!users.isEmpty()) {
            responseBuilder.delete(responseBuilder.length() - 2, responseBuilder.length());
        }

        responseBuilder.append("]"); // End of JSON array
        String responseMessage = this.getResponseMessage(responseBuilder.toString());
        response.setStatus(201);
        response.getOutputStream().println(responseMessage);
        response.setContentType("application/json");
    }

    public void destroy() {
    }

    private String getResponseMessage(String message) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("message", message);
        return responseObject.toString();
    }

    private HashMap<String, String> getCreateConnectionParameters(HttpServletRequest request) throws IOException {
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

    private HashMap<String, String> getAcceptConnectionParameters(HttpServletRequest request) throws IOException {
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
}