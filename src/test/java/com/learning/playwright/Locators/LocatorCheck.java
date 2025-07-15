package com.learning.playwright.Locators;

import com.learning.playwright.InitialTests.Baseclass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.Arrays;


public class LocatorCheck {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setupBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu")));
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void setup(){
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDownBrowser(){
        browser.close();
        playwright.close();
    }

    @Test()
    public void findElementByText(){
        page.navigate("https://www.amazon.in/");
       page.getByText("Hello, sign in").click();
       PlaywrightAssertions.assertThat(page.getByText("\n" +
               "        Create a free business account\n" +
               "      ")).isVisible();
    }

    @Test
    public void findElementByAltText(){
        page.navigate("https://www.flipkart.com/");
        page.getByAltText("Login").first().click();
        PlaywrightAssertions.assertThat(page.getByPlaceholder("Search for products, brands and more")).isVisible();
    }


    @Test
    public void findElementByTitle(){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Sign in").click();
        PlaywrightAssertions.assertThat(page.getByText("Email address *")).isVisible();
        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

}
