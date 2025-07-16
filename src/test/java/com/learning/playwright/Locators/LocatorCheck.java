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
        page.navigate("https://www.amazon.com/");
//        page.getByAltText("Continue shopping").click();
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

    @Test
    public void findElementByPlaceHolder(){
        page.navigate("https://demoqa.com/automation-practice-form");
        page.getByPlaceholder("First Name").fill("Koushick");
    }

    @Test
    public void findElementsByLabel(){
        page.navigate("https://qavalidation.com/demo-form/");
        page.getByLabel("Full Name").fill("Koushick");
    }


    @Test
    public void findElementByRole(){
        page.navigate("https://www.amazon.com/");
        page.getByAltText("Continue shopping").click();
        page.getByText("Hello, sign in").click();
        page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("Enter your mobile number or email")).fill("koushicksudharsanam@gmail.com");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("\n" +
                "                        Need help?\n" +
                "                    ")).click();
    }

}
