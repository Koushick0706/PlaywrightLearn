package com.learning.playwright.InitialTests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class FirstTest {
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu")));
        page = browser.newPage();
    }

    @AfterEach
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void checkPageTitle() {
        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();
        System.out.println(title);
        Assertions.assertTrue(title.equals("Practice Software Testing - Toolshop - v5.0"));
    }


    @Test
    public void searchForElement() {
        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();
        int count = page.locator("(//div[@class='container'])[3]/child::a").count();
        System.out.println("The Total Count for Search Results : " + count);
        Assertions.assertTrue(count > 0);
    }
}
