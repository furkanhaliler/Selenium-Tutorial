package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

  @Test(
      priority = 1,
      groups = {"positiveTests", "smokeTests"})
  public void positiveLoginTest() {

    // creating driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();

    // maximize browser window
    webDriver.manage().window().maximize();

    // open the test page
    String url = "http://the-internet.herokuapp.com/login";
    webDriver.get(url);

    // wait in order to see things happen slowly
    Util.sleepUntilSeconds(2);

    // enter username and password
    WebElement userName = webDriver.findElement(By.id("username"));
    userName.sendKeys("tomsmith");

    WebElement password = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
    password.sendKeys("SuperSecretPassword!");

    Util.sleepUntilSeconds(2);

    // click login button
    WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
    loginButton.click();

    Util.sleepUntilSeconds(2);

    // verifications:
    // new url
    String expectedUrl = "http://the-internet.herokuapp.com/secure";
    String actualUrl = webDriver.getCurrentUrl();

    Assert.assertEquals(expectedUrl, actualUrl, "Actual page url is not same with expected url.");

    // logout button is visible
    WebElement logoutButton = webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/a/i"));

    Assert.assertTrue(logoutButton.isDisplayed(), "Log out button is not visible on page.");

    // successful login message
    WebElement successMessage = webDriver.findElement(By.cssSelector("#flash"));

    String expectedMessage = "You logged into a secure area!";
    String actualMessage = successMessage.getText();

    Assert.assertTrue(
        actualMessage.contains(expectedMessage),
        "Actual message does not contain the expected message.");

    // close browser
    webDriver.quit();
  }

  @Parameters({"username", "password", "expectedMessage"})
  @Test(
      priority = 2,
      groups = {"negativeTests", "smokeTests"})
  public void negativeLoginTests(String username, String password, String expectedMessage) {

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

    Assert.assertTrue(
        actualMessage.contains(expectedMessage),
        "Actual message does not contain the expected message.");

    webDriver.quit();
  }
  //  There is no need to have two separate username and password method as we can do it with
  // @Parameter annotation.

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
  //    Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message does not contain
  // the expected message.");
  //
  //    webDriver.quit();
  //  }

}
