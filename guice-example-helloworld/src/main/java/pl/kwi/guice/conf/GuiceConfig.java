package pl.kwi.guice.conf;

import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Config listener for Guice.
 * 
 * @author Krzysztof Wisniewski
 *
 */
@WebListener
public class GuiceConfig extends GuiceServletContextListener {

    /* (non-Javadoc)
     * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
     */
    @Override
       protected Injector getInjector() {
              return Guice.createInjector(new GuiceServletModule());
       }

}
