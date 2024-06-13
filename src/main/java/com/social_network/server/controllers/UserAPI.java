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

@WebServlet(name = "users-api", value = "/api/users")
public class UserAPI extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<User> users = User.list("");
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("[");
        for (User user : users) {
            responseBuilder.append("{")
                    .append("\"username\": \"").append(user.getUsername()).append("\"")
                    .append("}, ");
        }
        if (!users.isEmpty() && responseBuilder.length() > 1) {
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