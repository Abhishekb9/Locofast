package com.interview;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Interview {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://github.com/login");
		driver.findElement(By.id("login_field")).sendKeys("Abhishekb9");
		driver.findElement(By.id("password")).sendKeys("Github@12345");
		driver.findElement(By.name("commit")).click();
	}
	
	@AfterMethod
	public void tearup()
	{
		driver.close();
	}
	
	@Test
	public void createRepo() throws InterruptedException
	{		
		String homePgText = "Discover interesting projects and people to populate your personal news feed.";
		Assert.assertEquals(homePgText, driver.findElement(By.xpath("//div[@class = 'Box p-5 mt-3']/h2")).getText());
		System.out.println("On Homepage !!!");

		driver.findElement(By.xpath("//*[@class = 'octicon octicon-plus']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(text(), 'New repository')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("repository_name")).sendKeys("Demo");
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String jscode = "window.scrollBy(0, document.body.scrollHeight)";
		jse.executeScript(jscode);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(), 'Create repository')]")).click();
		
		String repoName = driver.findElement(By.xpath("//a[text() = 'Demo']")).getText();
		Assert.assertEquals(repoName, "Demo");
		System.out.println("Repo created !!!");
	}
	
	
	@Test
	public void creatingIssue1() throws Exception
	{
		driver.findElement(By.xpath("(//span[@title = 'Demo'])[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text() = 'Issues']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text() = 'New issue']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("issue_title")).sendKeys("Issue_01");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String jscode = "window.scrollBy(0, document.body.scrollHeight)";
		jse.executeScript(jscode);
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//button[contains(text(),'Submit new issue')])[1]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class = 'js-issue-title']")).isDisplayed());
		System.err.println("Issue created !!");
	}
	
	@Test
	public void creatingIssue2() throws Exception
	{
		driver.findElement(By.xpath("(//span[@title = 'Demo'])[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text() = 'Issues']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text() = 'New issue']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("issue_title")).sendKeys("Issue_02");
		
		driver.findElement(By.id("issue_body")).sendKeys("#1"+Keys.ENTER+" This is related to Issue_01");
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String jscode = "window.scrollBy(0, document.body.scrollHeight)";
		jse.executeScript(jscode);
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//button[contains(text(),'Submit new issue')])[1]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class = 'js-issue-title']")).isDisplayed());
		System.out.println("Issue created and linked!!");
	}
	
	
	@Test
	public void navigating() throws Exception
	{
		driver.findElement(By.xpath("(//span[@title = 'Demo'])[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text() = 'Issues']")).click();
		Thread.sleep(1000);
		List<WebElement> issues = driver.findElements(By.xpath("//*[@data-hovercard-type = 'issue']"));
		issues.get(0).click();
		driver.findElement(By.xpath("//span[@class = 'js-issue-title']")).isDisplayed();
		driver.findElement(By.xpath("//a[@class = 'issue-link js-issue-link']")).click();
		Assert.assertEquals("Issue_01", driver.findElement(By.xpath("//span[@class = 'js-issue-title']")).getText());
		System.out.println("Navigated to issue 01");
	}
	
		
	@Test(dependsOnMethods = {"navigating"})
	public void deletingRepo() throws InterruptedException
	{				
		driver.findElement(By.xpath("(//span[@title = 'Demo'])[1]")).click();
		driver.findElement(By.xpath("//span[text() = 'Settings']")).click();
		Thread.sleep(2000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String jscode = "window.scrollBy(0, document.body.scrollHeight)";
		jse.executeScript(jscode);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//*[contains(text(), 'Delete this repository')])[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@type = 'text'])[10]")).sendKeys("Abhishekb9/Demo");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(), 'I understand the consequences, delete this repository')]")).click();
		System.out.println("Your repository \"Abhishekb9/Demo\" was successfully deleted.");
	}
	

}
