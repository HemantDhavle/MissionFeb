package com.qa.opencart.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils {
	
	private WebDriver driver;
	private static final String filePath = "";
	public Workbook book;
	public Sheet sheet;
	
	
	
	public ElementUtils(WebDriver driver)
	{
		this.driver = driver;
	}

	public void actionContextclicks()
	{
		Actions act = new Actions(driver);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("asd")));
	}
	
	public void getExcelData(String sheetName)
	{
		Object[][] getData=null;
		
		try {
			FileInputStream fs = new FileInputStream(filePath);
			book = WorkbookFactory.create(fs);
			sheet = book.getSheet(sheetName);
			
			
			int row = sheet.getRow(0).getLastCellNum();
			int col = sheet.getLastRowNum();
			
			
			
		} catch ( InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
