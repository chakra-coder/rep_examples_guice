package pl.kwi.guice.conf;

import pl.kwi.servlets.InputServlet;
import pl.kwi.servlets.OutputServlet;

import com.google.inject.servlet.ServletModule;

/**
 * Guice`s module for Servlets.
 * 
 * @author Krzysztof Wisniewski
 *
 */
public class GuiceServletModule extends ServletModule {

    /* (non-Javadoc)
     * @see com.google.inject.servlet.ServletModule#configureServlets()
     */
    @Override
       protected void configureServlets() {
              serve("/input.do").with(InputServlet.class);
              serve("/output.do").with(OutputServlet.class);
       }
}
