package com.learning.playwright.Locators;

import com.learning.playwright.InitialTests.Baseclass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


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
        browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
    }

    @BeforeEach
    public void setup(){
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDownBrowser(){
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("learn.zip")));
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
//        page.getByAltText("Continue shopping").click();
        page.getByText("Hello, sign in").click();
        page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("Enter your mobile number or email")).fill("koushicksudharsanam@gmail.com");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("\n" +
                "                        Need help?\n" +
                "                    ")).click();
    }


    @Test
    public void findbyTestID(){
        page.navigate("https://practicesoftwaretesting.com/");
        playwright.selectors().setTestIdAttribute("data-test");
        page.getByTestId("search-query").fill("Pliers");
        page.getByTestId("search-submit").click();
    }


    @Test
    public void findbyCollections(){
        page.navigate("https://practicesoftwaretesting.com/");
        playwright.selectors().setTestIdAttribute("data-test");
        page.waitForSelector("[data-test='product-name']");
        Locator allelements = page.getByTestId("product-name");
        int count = allelements.count();
        for (int i=0;i<count;i++){
            String text = allelements.nth(i).textContent().trim();
            if(text.equals("Bolt Cutters")){
                allelements.nth(i).click();
                System.out.println(page.getByTestId("product-name").textContent());
                System.out.println( page.getByTestId("unit-price").textContent());
                page.getByTestId("product-name").allTextContents().equals("Bolt Cutters");
                page.getByTestId("unit-price").allTextContents().equals("48.41");
                break;
            }
        }
    }

    @Test
    public void fillRegisterForm(){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByRole(AriaRole.MENUBAR,new Page.GetByRoleOptions().setName("Main menu"))
                .getByText("Sign in")
                .click();

        page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Register your account"))
                        .getByText("Register your account")
                                .click();

        playwright.selectors().setTestIdAttribute("data-test");
        String headerName = page.locator("h3:has-text('Customer registration')").innerText();
        System.out.println(headerName);
        headerName.equals("Customer registration");
        page.locator("#first_name").fill("Koushick");
        page.locator("[placeholder='Your last name *']").fill("Sudharsanam");
        page.getByTestId("dob").fill("1996-06-07");
        page.locator("#street").fill("test Street");
        page.locator("#postal_code").fill("600086");
        page.locator("[placeholder='Your City *']").fill("Chennai");
        page.getByTestId("state").fill("TamilNadu");
        page.locator("#country").selectOption("India");
        page.getByTestId("phone").fill("1234567890");
        page.locator("[placeholder='Your email *']").fill("test@test.com");
        page.locator("#password").fill("Testcheck@07");
        page.getByTestId("register-submit").click();
        List<String> allErrors = page.locator(".alert").allTextContents();
        for(String error : allErrors){
            System.out.println(error);
        }
    }

    @Test
    public void filterData(){
        page.navigate("https://practicesoftwaretesting.com/");
        playwright.selectors().setTestIdAttribute("data-test");
        page.waitForSelector("[data-test='product-name']");
        String element = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasText("Cutters"))
                .innerText();

        List<String> element1 = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasNotText("Pliers"))
                .allTextContents();

        List<String> element2 = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasNotText("Cutters"))
                .allTextContents();

        System.out.println(element);
        System.out.println(element1);
        System.out.println(element2);
    }


    @Test
    public void searchForPliers(){
        page.navigate("https://practicesoftwaretesting.com/");
        playwright.selectors().setTestIdAttribute("data-test");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Search")).click();
        Locator getdata = page.locator(".card");
        PlaywrightAssertions.assertThat(getdata).hasCount(4);
        List<String> alldata = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(alldata).allMatch(text -> text.contains("Pliers"));

        List<String> outofStock = page.locator(".card").filter(new Locator.FilterOptions().setHasNotText("Out of stock"))
                .allTextContents();

        System.out.println(outofStock);

        Locator outofStockName = page.locator(".card").filter(new Locator.FilterOptions().setHasText("Out of stock"))
                .getByTestId("product-name");

        PlaywrightAssertions.assertThat(outofStockName).hasCount(1);
        PlaywrightAssertions.assertThat(outofStockName).hasText("Long Nose Pliers");
    }

}
