package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests {

  WebDriver webDriver;

  @BeforeTest(alwaysRun = true)
  private void setUp() {
    // creating driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    webDriver = new ChromeDriver();

    // maximize browser window
    webDriver.manage().window().maximize();

    // implicit wait
    // webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }

  @AfterMethod(alwaysRun = true)
  private void shutdown() {

    // close browser
    webDriver.quit();
  }

  @Test(
      priority = 1,
      groups = {"positiveTests", "smokeTests"})
  public void positiveLoginTest() {

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

    // click login button, explicitly wait
    WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));

    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.elementToBeClickable(loginButton));

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
  }

  @Parameters({"username", "password", "expectedMessage"})
  @Test(
      priority = 2,
      groups = {"negativeTests", "smokeTests"})
  public void negativeLoginTests(String username, String password, String expectedMessage) {

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
  }
  //  There is no need to have two separate username and password method as we can do it with
  // @Parameter annotation.

  //  @Test(priority = 1, groups = {"negativeTests", "smokeTests"})
  //  public void incorrectPasswordTest() {
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
  //  }

}
