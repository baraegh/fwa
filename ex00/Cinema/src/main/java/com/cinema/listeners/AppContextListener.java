package com.cinema.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cinema.config.Config;

@WebListener
public class AppContextListener implements ServletContextListener {
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(Config.class);
        sce.getServletContext().setAttribute("springContext", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ApplicationContext context = 
            (ApplicationContext) sce.getServletContext().getAttribute("springContext");
        
        if (context instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext)context).close();
        }
            
    }
}
