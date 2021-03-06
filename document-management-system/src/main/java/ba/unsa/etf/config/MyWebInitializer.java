package ba.unsa.etf.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class MyWebInitializer implements WebApplicationInitializer {
	 
	private String TMP_FOLDER = "/tmp"; 
    private int MAX_UPLOAD_SIZE = 512000000; //512MB
     
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
         
        ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", (Servlet) new DispatcherServlet(new GenericWebApplicationContext()));
 
        appServlet.setLoadOnStartup(1);
         
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, 
          MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
         
        appServlet.setMultipartConfig(multipartConfigElement);
    }

}