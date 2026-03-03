package com.cinema.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.context.ApplicationContext;

import com.cinema.models.Image;
import com.cinema.models.User;
import com.cinema.services.ImageService;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/images/*")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 10,   // 10MB
    maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class ImageServlet extends HttpServlet {
    private ImageService    imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext      context = config.getServletContext();
            ApplicationContext  springContext = (ApplicationContext) context.getAttribute("springContext");

            if (springContext == null) {
                throw new ServletException("springContext is NULL - listener didn't run!");
            }

            imageService = springContext.getBean(ImageService.class);
        } catch (Exception e) {
            e.printStackTrace();
           throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String          fileName = rq.getPathInfo().substring(1);
        Optional<Image> imgOpt = imageService.getByFileName(fileName);

        imgOpt.ifPresent(img -> {
            rs.setContentType(img.getMimeType());
            rs.setHeader("Content-Disposition", "inline; filename=\"" + img.getFileName() + "\"");
            try {
                Files.copy(Paths.get(img.getFilePath()), rs.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {
        User user = (User)rq.getSession(false).getAttribute("user");

        if (user != null) {
            Part filepart = rq.getPart("file");

            imageService.save(
                new Image(
                    user.getId(),
                    filepart.getSubmittedFileName(),
                    filepart.getContentType(),
                    filepart.getSize(),
                    ""
                ),
                filepart.getInputStream()
            );
        }
        rs.sendRedirect(rq.getContextPath() + "/profile");
    }
}
