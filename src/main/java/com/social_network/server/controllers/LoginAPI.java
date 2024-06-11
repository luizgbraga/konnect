package com.social_network.server.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.social_network.server.HibernateUtil;
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
import org.json.JSONObject;

@WebServlet(name = "login-api", value = "/api/user/login")
public class LoginAPI extends HttpServlet {
    public void init() {}
  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
            NoSuchElementException {
        try {
            HashMap<String, String> parameters = this.getUserLoginParameters(request);
            String username = parameters.get("username");
            String password = parameters.get("password");
            User user = User.login(username, password);
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("{")
                    .append("\"id\": \"").append(user.getId()).append("\", ")
                    .append("\"username\": \"").append(user.getUsername()).append("\"")
                    .append("}");

            String responseMessage = this.getResponseMessage(responseBuilder.toString());
            response.setStatus(201);
            response.setContentType("application/json");
            response.getOutputStream().println(responseMessage);
        } catch (NoSuchElementException e) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getOutputStream().println("__error__: Username or password are incorrect");
        } finally {

        }
    }

    public void destroy() {
    }

    private String getResponseMessage(String message) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("message", message);
        return responseObject.toString();
    }

    private HashMap<String, String> getUserLoginParameters(HttpServletRequest request) throws IOException {
        StringBuilder jsonDataBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonDataBuilder.append(line);
        }
        String jsonData = jsonDataBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", password);
        return parameters;
    }
}