package com.cinema.servlets;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class profileServlet extends HttpServlet {
    
    @Override
    public void init(ServletConfig config) {
        // to be completed
    }

    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rq.getRequestDispatcher("/WEB-INF/jsp/profile.jsp")
            .forward(rq, rs);
    }
}
