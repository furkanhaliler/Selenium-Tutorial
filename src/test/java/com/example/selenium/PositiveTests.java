package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

  @Test
  public void loginTest() {

    // creating driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();

    // maximize browser window
    webDriver.manage().window().maximize();

    // open the test page
    String url = "http://the-internet.herokuapp.com/login";
    webDriver.get(url);

    // wait in order to see things happen slowly
    sleepUntilSeconds(2);

    // enter username and password
    WebElement userName = webDriver.findElement(By.id("username"));
    userName.sendKeys("tomsmith");

    WebElement password = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
    password.sendKeys("SuperSecretPassword!");

    sleepUntilSeconds(2);

    // click login button
    WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
    loginButton.click();

    sleepUntilSeconds(2);

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

    Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not same with expected message.");

    //close browser
    webDriver.quit();
  }

  private void sleepUntilSeconds(long seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
