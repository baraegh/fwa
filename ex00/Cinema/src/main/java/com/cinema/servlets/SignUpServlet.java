package com.cinema.servlets;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.cinema.models.User;
import com.cinema.services.UserService;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext context = config.getServletContext();
            ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
            
            if (springContext == null) {
                throw new ServletException("springContext is NULL - listener didn't run!");
            }
            
            this.userService = springContext.getBean(UserService.class);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    
    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) 
        throws ServletException, IOException {
    
        rq.getRequestDispatcher("/WEB-INF/html/signUp.html")
           .forward(rq, rs);
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) 
        throws ServletException, IOException 
    {
        User user = new User();
        
        user.setFirstName((String) rq.getParameter("firstName"));
        user.setLastName((String) rq.getParameter("lastName"));
        user.setEmail((String) rq.getParameter("email"));
        user.setPhoneNumber((String) rq.getParameter("phone"));
        user.setPassword((String) rq.getParameter("password"));

        Optional<User> userOpt = userService.signUp(user);
        
        if (userOpt.isPresent()) {
            rs.sendRedirect("signIn");
        } else {
            rq.getRequestDispatcher("/WEB-INF/html/signUp.html")
            .forward(rq, rs);
        }
        
    }
}
