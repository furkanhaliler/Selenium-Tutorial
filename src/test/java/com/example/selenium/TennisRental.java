package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TennisRental {

  @Test
  public void rent() {

    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver webDriver = new ChromeDriver();

    webDriver.manage().window().maximize();

    String url = "https://online.spor.istanbul/uyegiris";

    webDriver.get(url);
    webDriver.findElement(By.xpath("//*[@id=\"txtTCPasaport\"]")).sendKeys("11111111111");
    webDriver
        .findElement(By.xpath("/html/body/form/div[4]/div/div[3]/input"))
        .sendKeys("wrongpassword");
    webDriver.findElement(By.xpath("//*[@id=\"btnGirisYap\"]")).click();
    String url2 = "https://online.spor.istanbul/satiskiralik";
    webDriver.get(url2);

    Util.sleepUntilSeconds(3);
  }

}
