package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory 
{
	public Properties prop;
	public WebDriver driver;
	private static ThreadLocal<WebDriver> lt  = new ThreadLocal<WebDriver> ();
	
	public WebDriver init_browser(Properties prop)
	{
		String browserName  = prop.getProperty("browser");
		OptionManager om = new OptionManager(prop);		
		if(browserName.equalsIgnoreCase("chrome"))
		{	
			WebDriverManager.chromedriver().setup();
			 lt.set(new ChromeDriver(om.chromeOptions()));	
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			lt.set(new FirefoxDriver(om.firefoxOptions()));	
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			lt.set(new EdgeDriver(om.edgeOptions()));	
		}
		else
		{
			System.out.println("Please pass the correct browser");
		}
		return getDriver();
	}
	
	public static synchronized WebDriver getDriver()
	{
		return lt.get();
	}
	public Properties Init_Prop()
	{
		FileInputStream fs = null;
		prop = new Properties();
		String envName = System.getProperty("env");
		try {
		if(envName==null)
		{
			System.out.println("We are running on production environment");
			
				fs = new FileInputStream("./src/test/resources/config/prod_config.properties");
			}
		else
		{
			System.out.println("We are running on "+envName);
			switch (envName.toLowerCase()) {
			case "qa":
				fs = new FileInputStream("./src/test/resources/config/qa_config.properties");
				break;
			case "int":
				fs = new FileInputStream("./src/test/resources/config/int_config.properties");
				break;
			case "stage":
				fs = new FileInputStream("./src/test/resources/config/staging_config.properties");
				break;
			default:
				break;
			}
		}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	try {
		prop.load(fs);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return prop;
		
	}
	
	public void screenshot()
	{
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String path  = System.currentTimeMillis()+"/screenshot/"+".png";
		File des = new File(path);
		try {
			FileUtils.copyFile(src, des);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
