package pl.kwi.intg.pages;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import pl.kwi.intg.utils.AbstrIntgTestPage;

public class CreatePage extends AbstrIntgTestPage {

	public CreatePage(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
	}

	@Override
	public void checkIfPageLoaded() {
		
		// Wait
        wait.until(ExpectedConditions.titleIs("Hello World - Create"));
        
        // Conditions
        text = driver.findElement(By.id("titleText")).getText();
        assertEquals("Hello World", text); 
        
        text = driver.findElement(By.id("subtitleText")).getText();
        assertEquals("Page: Create", text); 

	}

}
