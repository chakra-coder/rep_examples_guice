package pl.kwi.guice.conf;

import pl.kwi.servlets.CreateServlet;
import pl.kwi.servlets.DeleteServlet;
import pl.kwi.servlets.EditServlet;
import pl.kwi.servlets.TableServlet;
import pl.kwi.servlets.ViewServlet;

//import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

/**
 * Guice`s module for Servlets.
 * 
 * @author Krzysztof Wisniewski
 * 
 */
public class GuiceServletModule extends ServletModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.servlet.ServletModule#configureServlets()
	 */
	@Override
	protected void configureServlets() {
		
		// JPA configuration
		install(new JpaPersistModule("jpaUnit"));
		// session-per-transaction
		bind(JPAInitializer.class).asEagerSingleton();
		// session-per-http-request
		// Here is problem with undeployment of application.
//		filter("/*").through(PersistFilter.class);
		
		// Servlets configuration
		serve("/table.do").with(TableServlet.class);
		serve("/create.do").with(CreateServlet.class);
		serve("/view.do").with(ViewServlet.class);
		serve("/edit.do").with(EditServlet.class);
		serve("/delete.do").with(DeleteServlet.class);
	}
	
}
