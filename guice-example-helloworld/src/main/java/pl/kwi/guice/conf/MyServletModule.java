package pl.kwi.guice.conf;

import pl.kwi.servlets.InputServlet;
import pl.kwi.servlets.OutputServlet;

import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {

       @Override
       protected void configureServlets() {
              serve("/input.do").with(InputServlet.class);
              serve("/output.do").with(OutputServlet.class);
       }
}
