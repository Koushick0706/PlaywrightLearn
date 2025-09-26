package com.learning.playwright.Waits;

import com.learning.playwright.InitialTests.Baseclass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.LoadState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.List;


public class WaitChecks {

    private static Playwright playwright;
    private static BrowserContext browserContext;
    private static Browser browser;
    Page page;

    @BeforeAll
    public static void setupBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge"));
        browserContext = browser.newContext();
        browserContext.tracing().start(new Tracing.StartOptions().setSnapshots(true).setScreenshots(true).setSources(true));
    }

    @BeforeEach
    public void setupPage(){
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
        page.waitForSelector(".card-img-top");
    }

    @AfterAll
    public static void teardownBrowser(){
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("waits.zip")));
        browserContext.close();
        browser.close();
    }

    @Test
    public void waitTestOne(){
        List<String> allproducts = page.locator(".card-img-top").all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();

        System.out.println(allproducts);
        Assertions.assertThat(allproducts).contains("Combination Pliers","Bolt Cutters");
    }


    @Test
    public void waitTestTwo(){
        Locator screwdriver = page.getByLabel("Screwdriver");
        screwdriver.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        List<String> allproducts = page.locator(".card-img-top").all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();
        System.out.println(allproducts);
        Assertions.assertThat(allproducts).contains("Phillips Screwdriver");
    }
}
