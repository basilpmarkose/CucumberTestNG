package com.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public static WebDriver driver;
	Properties propertyFile = new Properties();
	WebDriverWait wait;

	public TestBase() {
		try {
			FileInputStream stream = new FileInputStream("/BDD_Cucumber_TestNG/src/main/java/com/qa/config/config.properties");
			propertyFile.load(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void InitilizeWebDriver() {
		String Browser = propertyFile.getProperty("browser");
		if(Browser.equals("Chrome")) {
			driver = new ChromeDriver();
		}
		else if(Browser.equals("FF")) {
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtils.implicitlyWait));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtils.pageLoadTimeout));
		wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.explicitWait));
	}

	public void NavigateTo(String url) {
		driver.get(url);
	}

	public boolean isPresent(List<WebElement> element) {
		return element.size()>0;		
	}

	public void CaptureScreenshort() {
		//Take the screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		//Copy the file to a location and use try catch block to handle exception
		try {
			FileUtils.copyFile(screenshot, new File("C:\\projectScreenshots\\homePageScreenshot.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void ReadFromProperty(String PropertyFile){
		try {
			FileInputStream stream = new FileInputStream(PropertyFile);
			propertyFile.load(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
