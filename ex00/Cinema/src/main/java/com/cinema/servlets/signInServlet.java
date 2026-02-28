package com.cinema.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.context.ApplicationContext;

import com.cinema.services.UserService;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/signIn")
public class signInServlet extends HttpServlet {
    
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext      context = config.getServletContext();
            ApplicationContext  springContext = (ApplicationContext) context.getAttribute("springContext");
            
            if (springContext == null) {
                throw new ServletException("springContext is NULL - listener didn't run!");
            }

            userService = springContext.getBean(UserService.class);
        } catch (Exception e) {
            e.printStackTrace();
           throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        PrintWriter out = rs.getWriter();

        out.println("HELLO");
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) {

    }
}
