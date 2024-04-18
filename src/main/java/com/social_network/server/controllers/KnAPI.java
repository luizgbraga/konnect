package com.social_network.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import com.social_network.server.HibernateUtil;
import com.social_network.server.entities.*;
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

@WebServlet(name = "kn-api", value = "/api/kn")
public class KnAPI extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        ArrayList<KnUser> knUsers = KnUser.getFromUser( userId);
        ArrayList<Kn> kns = Kn.list();
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("[");
        for (KnUser knUser : knUsers) {
            // Constructing JSON-like representation for each user
            String name = "Not found";
            for (Kn kn : kns) {
                if (kn.getId().equals(knUser.getKnId())) {
                    name = kn.getName();
                    break; // Found the user, no need to continue iterating
                }
            }
            responseBuilder.append("{")
                    .append("\"id\": \"").append(knUser.getKnId()).append("\", ")
                    .append("\"name\": \"").append(name).append("\"")
                    .append("}, ");
        }
        if (!knUsers.isEmpty()) {
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

}