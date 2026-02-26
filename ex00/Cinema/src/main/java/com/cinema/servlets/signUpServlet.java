package com.cinema.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.cinema.models.User;
import com.cinema.services.UserService;

@WebServlet("/signUp")
public class signUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        this.userService = springContext.getBean(UserService.class);
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
        
        user.setName((String) rq.getParameter("name"));
        user.setEmail((String) rq.getParameter("email"));
        user.setPhoneNumber((String) rq.getParameter("phoneNumber"));
        user.setPassword((String) rq.getParameter("password"));

        Optional<User> userOpt = userService.signUp(user);
        
        if (userOpt.isPresent()) {
            // rq.getRequestDispatcher("/WEB-INF/html/signIn.html")
            //     .forward(rq, rs);
            rs.sendRedirect("signIn.html");
        } else {
            rq.getRequestDispatcher("/WEB-INF/html/signUp.html")
            .forward(rq, rs);
        }
        
    }
}
