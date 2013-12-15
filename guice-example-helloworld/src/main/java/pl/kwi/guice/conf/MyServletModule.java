package pl.kwi.guice.conf;

import pl.kwi.servlets.InputServlet;

import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {

       @Override
       protected void configureServlets() {
//              serve("*.html").with(InputServlet.class);
       }
}
