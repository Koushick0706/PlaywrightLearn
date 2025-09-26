package com.learning.playwright.Locators;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.List;

public class LocatorsTest {

    private static Playwright playwright;
    private static BrowserContext browserContext;
    private static Browser browser;
    Page page;

    @BeforeAll
    public static void setup(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge"));
        browserContext = browser.newContext();
        browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
    }

    @BeforeEach
    public void eachMethod(){
        page = browserContext.newPage();
        page.navigate("https://playwright.dev/");

    }

    @Test
    public void testPlaywrightWebsite(){
        page.getByText("Docs").click();
        PlaywrightAssertions.assertThat(page).hasURL("https://playwright.dev/docs/intro");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Node.js")).click();
        page.waitForSelector(".dropdown__menu");
        List<Locator> alllocator = page.locator("xpath=//*[@class='dropdown__menu']/child::li").all();
        for(Locator changeProgram : alllocator){
            String getProgram = changeProgram.textContent();
            System.out.println(getProgram);
            if(getProgram.equals("Java")){
                changeProgram.click();
            }

        }

        boolean visOrNot = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Java")).isVisible();

        Assertions.assertTrue(visOrNot,"The Text Contains Java");


    }


    @Test
    public void searchInPlayWrightPageAndScroll(){
        page.getByText("Search").click();
        page.getByPlaceholder("Search docs").fill("Trace viewer");
        page.locator(".DocSearch-Modal").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Locator allllinks = page.locator(".DocSearch-Hit-title");
        page.waitForTimeout(5000);
        List<Locator> getsearchList = allllinks.all();
        for(Locator getsearch : getsearchList) {
            if (getsearch.isVisible()) {
                System.out.println(getsearch.textContent());
                if (getsearch.textContent().equals("Opening Trace Viewer\u200B")) {
                    getsearch.click();
                    page.waitForLoadState(LoadState.NETWORKIDLE);
                    Locator record = page. getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Viewing remote traces"));
                    page.waitForTimeout(5000);
                    record.scrollIntoViewIfNeeded();
                    boolean istrue = record.isVisible();
                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get("screenshot.png")));

                    Assertions.assertTrue(istrue, "Visited to Introduction for Tracing page");
                }
            }
        }

    }

    @AfterAll
    public static void tearDown(){
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("demo.zip")));
        browserContext.close();
        browser.close();
    }
}
