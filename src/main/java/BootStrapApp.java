


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class BootStrapApp implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {


		AnnotationConfigWebApplicationContext context
				= new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);

		container.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = container
				.addServlet("dispatcher", new DispatcherServlet(context));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");


//		// Create the 'root' Spring application context
//		AnnotationConfigWebApplicationContext rootContext =
//				new AnnotationConfigWebApplicationContext();
//		rootContext.register(AppConfig.class);
//
//
//		// Manage the lifecycle of the root application context
//		servletContext.addListener(new ContextLoaderListener(rootContext));
//
//		ServletRegistration.Dynamic dispatcher = servletContext
//				.addServlet("dispatcher", new DispatcherServlet(rootContext));
//
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");

	}
}
