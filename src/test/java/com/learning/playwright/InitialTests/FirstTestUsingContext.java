package com.learning.playwright.InitialTests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class FirstTestUsingContext {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu")));
        browserContext = browser.newContext();

    }

    @BeforeEach
    public void setupPage(){
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDownBrowser() {
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
        int count = page.locator(".card-title").count();
        System.out.println("The Total Count for Search Results : " + count);
        Assertions.assertTrue(count > 0);
    }
}
