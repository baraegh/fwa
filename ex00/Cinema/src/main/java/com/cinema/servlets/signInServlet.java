package com.cinema.servlets;

import java.io.IOException;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cinema.models.User;
import com.cinema.services.UserService;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/signIn")
public class signInServlet extends HttpServlet {
    
    private UserService             userService;
    private BCryptPasswordEncoder   encoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext      context = config.getServletContext();
            ApplicationContext  springContext = (ApplicationContext) context.getAttribute("springContext");
            
            if (springContext == null) {
                throw new ServletException("springContext is NULL - listener didn't run!");
            }

            userService = springContext.getBean(UserService.class);
            encoder = springContext.getBean(BCryptPasswordEncoder.class);
        } catch (Exception e) {
            e.printStackTrace();
           throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {
        rq.getRequestDispatcher("/WEB-INF/html/signIn.html")
            .forward(rq, rs);
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {
        String email = rq.getParameter("email");
        String password = rq.getParameter("password");

        Optional<User> userOpt = userService.getByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (encoder.matches(password, user.getPassword())) {
                HttpSession session = rq.getSession();

                session.setAttribute("user", user);
                rs.sendRedirect("profile");
            } else {
                rq.getRequestDispatcher("/WEB-INF/html/signIn.html")
                    .forward(rq, rs);
            } 
        } else {
            rq.getRequestDispatcher("/WEB-INF/html/signIn.html")
                .forward(rq, rs);
        }
    }
}
