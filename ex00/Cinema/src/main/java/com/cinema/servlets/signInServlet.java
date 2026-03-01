package com.cinema.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cinema.models.LoginEntry;
import com.cinema.models.User;
import com.cinema.services.LoginEntryService;
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
    
    private UserService             userService;
    private LoginEntryService       loginEntryService;
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
            loginEntryService = springContext.getBean(LoginEntryService.class);
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
        
        if (userOpt.isPresent() && encoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            
            rq.getSession().setAttribute("user", user);
            loginEntryService.save(new LoginEntry(
                user.getId(),
                rq.getRemoteAddr(),
                LocalDate.now(),
                LocalTime.now()
            ));
            rs.sendRedirect("profile");
        } else {
            rq.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(rq, rs);
        }
    }
}
