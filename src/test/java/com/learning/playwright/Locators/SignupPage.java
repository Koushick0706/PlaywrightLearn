package com.learning.playwright.Locators;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SignupPage {

    private static Playwright playwright;
    private static BrowserContext browserContext;
    private static Browser browser;
    private static Page page;

    @BeforeAll
    public static void setup(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge"));
        browserContext = browser.newContext();
        browserContext.tracing().start(new Tracing.StartOptions().setSnapshots(true).setScreenshots(true).setSources(true));
    }

    @BeforeEach
    public void Initilize(){
        page = browserContext.newPage();
        page.navigate("https://freelance-learn-automation.vercel.app/login");

    }

    @Test
    public void fillUpForm(){
        boolean isvisable = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Learn Automation Courses")).isVisible();
        if(isvisable) {
            page.getByText("New user? Signup").click();
            page.waitForTimeout(5000);
            boolean pageLanded = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Sign Up")).isVisible();
            Assertions.assertTrue(pageLanded,"Landed signUp Page");
            if(pageLanded) {
                page.getByPlaceholder("Name").fill("Tanjiro Kamado");
                page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("email")).fill("Nezuko@demonslayer.com");
                page.getByTitle("Password must be atleast 6 characters").fill("NezukoKamado@0101");
                Locator options = page.locator("div.interest-div");
                List<String> interestsToSelect = Arrays.asList(
                        "Cypress", "AWS", "SQL", "WDIO", "test Category 13",
                        "test Category 15", "test Category 17", "test Category 20", "test Category 27", "test Category 23"
                );
                for(String interest : interestsToSelect){
                    options.filter(new Locator.FilterOptions().setHasText(interest))
                            .locator("input[type='checkbox']")
                            .check();
                }

                page.locator("xpath=//*[@id='gender2']").click();

                page.waitForTimeout(5000);
                List<String> hobbiesToSelect = Arrays.asList(
                        "Playing", "Reading", "Singing"
                );
                page.locator("#state").selectOption("Karnataka");
                page.locator("#hobbies").selectOption(hobbiesToSelect.toArray(new String[0]));
                page.locator("#hobbies").scrollIntoViewIfNeeded();

                page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Sign up")).click();
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("fillup.png")));

                page.waitForTimeout(2000);

            }
        }
    }

    @AfterAll
    public static void teardown(){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("signup.png")));
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("signup.zip")));
        browserContext.close();
        browser.close();

    }
}
