package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {

  @Parameters({"username", "password", "expectedMessage"})
  @Test(priority = 2, groups = {"negativeTests"})
  public void incorrectUserNameTest(String username, String password, String expectedMessage) {

    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();

    webDriver.manage().window().maximize();

    String url = "http://the-internet.herokuapp.com/login";
    webDriver.get(url);

    WebElement userNameElement = webDriver.findElement(By.xpath("//*[@id=\"username\"]"));
    userNameElement.sendKeys(username);

    WebElement passwordElement = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
    passwordElement.sendKeys(password);

    WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
    loginButton.click();

    WebElement failMessage = webDriver.findElement(By.xpath("//*[@id=\"flash\"]"));

    String actualMessage = failMessage.getText();

    Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain the expected message.");

    webDriver.quit();
  }
//  There is no need to have two separate username and password method as we can do it with @Parameter annotation.

//  @Test(priority = 1, groups = {"negativeTests", "smokeTests"})
//  public void incorrectPasswordTest() {
//
//    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//    WebDriver webDriver = new ChromeDriver();
//
//    webDriver.manage().window().maximize();
//
//    String url = "http://the-internet.herokuapp.com/login";
//    webDriver.get(url);
//
//    WebElement userName = webDriver.findElement(By.xpath("//*[@id=\"username\"]"));
//    userName.sendKeys("tomsmith");
//
//    WebElement password = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
//    password.sendKeys("wrongPassword");
//
//    WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
//    loginButton.click();
//
//    WebElement failMessage = webDriver.findElement(By.xpath("//*[@id=\"flash\"]"));
//
//    String expectedMessage = "Your password is invalid!";
//    String actualMessage = failMessage.getText();
//
//    Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain the expected message.");
//
//    webDriver.quit();
//  }
}
