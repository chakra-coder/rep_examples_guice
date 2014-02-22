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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import pl.kwi.db.guice.test.DbUnitUtil;
import pl.kwi.intg.pages.CreatePage;
import pl.kwi.intg.pages.DeletePage;
import pl.kwi.intg.pages.EditPage;
import pl.kwi.intg.pages.TablePage;
import pl.kwi.intg.pages.ViewPage;
import pl.kwi.intg.utils.IntgTestUtils;

@RunWith(Arquillian.class)
public class IntgTests {
	
	
	private final static String PATH_HOST = System.getProperty("test.intg.path.host");
	private final static String PATH_CONTEXT = System.getProperty("test.intg.path.context");
	private final static String DB_URL = System.getProperty("test.intg.db.url");
	private final static String DB_USERNAME = System.getProperty("test.intg.db.username");
	private final static String DB_PASSWORD = System.getProperty("test.intg.db.password");
	private final static String DB_DRIVER = System.getProperty("test.intg.db.driver");
	private final static String WAR_FILE = PATH_CONTEXT + ".war";
	
	private TablePage tablePage;
	private CreatePage createPage;
	private ViewPage viewPage;
	private EditPage editPage;
	private DeletePage deletePage;
	
	
	@Deployment
    public static Archive<?> createDeployment() {
		
		MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadMetadataFromPom("pom.xml")
				.includeDependenciesFromPom("pom.xml");
		
        WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_FILE)
        .addPackages(true, "pl.kwi")
        .addAsLibraries(resolver.resolveAsFiles(new ScopeFilter("", "compile", "runtime")))
        .addAsResource("intg-tests/persistence.xml", "META-INF/persistence.xml");
        
        IntgTestUtils.addFilesToWar(war, new File("src/main/webapp"));
        
        return war;
        
    }
	
	
	@Before
	public void setUp(){
		
		WebDriver driver = new FirefoxDriver();
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);		
		
		tablePage = new TablePage(driver, wait);
		createPage = new CreatePage(driver, wait);
		viewPage = new ViewPage(driver, wait);
		editPage = new EditPage(driver, wait);
		deletePage = new DeletePage(driver, wait);
		
	}
	
	@Test
	public void tableTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();
		
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		
		tablePage.closeBrowser();
		
	}
	
	@Test
	public void createTestCase() {
		
		DbUnitUtil.clearDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
				
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);

		
		tablePage.checkIfPageLoaded();
		tablePage.checkTextInFieldById("noData", "No Data");
		tablePage.clickLinkByText("Create");
		
		createPage.checkIfPageLoaded();
		createPage.pressButtonById("back");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkTextInFieldById("noData", "No Data");
		tablePage.clickLinkByText("Create");
		
		createPage.checkIfPageLoaded();
		createPage.typeTextInFieldById("name", "User1");
		createPage.pressButtonById("create");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label", "User1");
		
		tablePage.closeBrowser();
		
	}
	
	@Test
	public void readTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("View");
		
		viewPage.checkIfPageLoaded();
		viewPage.checkAttributeInElementdById("name", "value", "User1");
		viewPage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		
		tablePage.closeBrowser();
		
				
	}
	
	@Test
	public void readTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("View");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("View");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
		
	@Test
	public void updateTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Edit");
		
		editPage.checkIfPageLoaded();
		editPage.checkAttributeInElementdById("name", "value", "User1");
		editPage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Edit");
		
		editPage.checkIfPageLoaded();
		editPage.clearTextInFieldById("name");
		editPage.typeTextInFieldById("name", "User4");
		editPage.pressButtonById("update");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User4");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void updateTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("Edit");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("Edit");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void deleteTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Delete");
		
		deletePage.checkIfPageLoaded();
		deletePage.checkBodyInElementByXPath("//div[@id='confirmationText']", "Do you really want delete user: User1?");
		deletePage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Delete");
		
		deletePage.checkIfPageLoaded();
		deletePage.checkBodyInElementByXPath("//div[@id='confirmationText']", "Do you really want delete user: User1?");
		deletePage.pressButtonById("delete");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkElementNotExistsByXPath("//label[@for='selectedUsersIds1']");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void deleteTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("Delete");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("Delete");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
	

}
