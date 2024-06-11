package com.social_network.server.controllers;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.social_network.server.HibernateUtil;
import com.social_network.server.entities.*;
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
        if (connections == null) {
            responseBuilder.append("[]");
            String responseMessage = this.getResponseMessage(responseBuilder.toString());
            response.setStatus(201);
            response.getOutputStream().println(responseMessage);
            response.setContentType("application/json");
            return;
        }
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
            String action = request.getParameter("action");
            System.out.println(userFromId);
            System.out.println(userToId);

            ConnectsTo connection = ConnectsTo.get(userFromId, userToId);
            System.out.println(connection);
            if (action.equals("delete")) {
                session.createQuery("DELETE FROM ConnectsTo WHERE userFromId = :userFromId AND userToId = :userToId")
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .executeUpdate();
            } else {
                connection.setStatus("active");
                session.merge(connection); // Use merge to update detached entity
                ArrayList<ConnectsTo> connections = ConnectsTo.listActives();
                System.out.println(connections);
                Map<String, List<List<String>>> result = ConnectsTo.checkGroups(connections);
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
            }
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