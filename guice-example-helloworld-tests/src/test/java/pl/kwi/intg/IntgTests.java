package pl.kwi.intg;


import java.io.File;



import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Arquillian.class)
public class IntgTests {
	
	
	private final static String PATH_HOST = System.getProperty("test.intg.path.host");
	private final static String PATH_CONTEXT = System.getProperty("test.intg.path.context");
	private final static String WAR_FILE = PATH_CONTEXT + ".war";
	
	private InputIntgTestPage inputPage;
	private OutputIntgTestPage outputPage;
	
	
	@Deployment
    public static Archive<?> createDeployment() {
		
		MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadMetadataFromPom("pom.xml")
				.includeDependenciesFromPom("pom.xml");
		
        WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_FILE)
        .addPackages(true, "pl.kwi")
        .addAsLibraries(resolver.resolveAsFiles(new ScopeFilter("", "compile", "runtime")));
        
        IntgTestUtils.addFilesToWar(war, new File("src/main/webapp"));
                
        return war;
        
    }
	
	
	@Before
	public void setUp(){
		
		WebDriver driver = new FirefoxDriver();
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);		
		
		inputPage = new InputIntgTestPage(driver, wait);
		outputPage = new OutputIntgTestPage(driver, wait);
		
	}
	
	@Test
	public void typeNameToInputPageAndCheckOutputPage() {
		
		inputPage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
	
		inputPage.checkIfPageLoaded();
		inputPage.typeTextInFieldById("name", "Chris");
		inputPage.pressButtonById("submit");
		
		outputPage.checkIfPageLoaded();
		outputPage.checkTextInFieldById("name", "Hello World Chris");
		outputPage.pressButtonById("submit");
		
		inputPage.checkIfPageLoaded();
		
		inputPage.closeBrowser();
		
	}
	
	@Test
	public void typeNoNameToInputPage() {
		
		inputPage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
			
		inputPage.checkIfPageLoaded();
		inputPage.typeTextInFieldById("name", "");
		inputPage.pressButtonById("submit");
		
		inputPage.checkIfPageLoaded();
		inputPage.checkTextInFieldById("name.errors", "Please fill this field");
		
		inputPage.closeBrowser();
		
	}
	

}
