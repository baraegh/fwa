package com.cinema.servlets;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cinema.models.Image;
import com.cinema.models.User;

import com.cinema.services.ImageService;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    // private LoginEntryService       loginEntryService;
    private ImageService            imageService;


    /*
        TODO: move login_history from signInSevlet(POST)
        to profile servlet
    */

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext      context = config.getServletContext();
            ApplicationContext  springContext = (ApplicationContext) context.getAttribute("springContext");

            if (springContext == null) {
                throw new ServletException("springContext is NULL - listener didn't run!");
            }

            // loginEntryService = springContext.getBean(LoginEntryService.class);
            imageService = springContext.getBean(ImageService.class);
        } catch (Exception e) {
            e.printStackTrace();
           throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        User user = (User) rq.getSession(false).getAttribute("user");
        if (user != null) {
            List<Image> images = imageService.getByUserId(user.getId());
            images.forEach(img -> {
                long size = img.getSize();
                if (size >= 1024 * 1024) {
                    img.setFormattedSize(String.format("%.1f MB", size / (1024.0 * 1024)));
                } else if (size >= 1024) {
                    img.setFormattedSize(String.format("%.1f KB", size / 1024.0));
                } else {
                    img.setFormattedSize(size + " B");
                }
            });

            rq.getSession().setAttribute(
                "images", 
                images
            );
        }

        rq.getRequestDispatcher("/WEB-INF/jsp/profile.jsp")
            .forward(rq, rs);
    }


}
