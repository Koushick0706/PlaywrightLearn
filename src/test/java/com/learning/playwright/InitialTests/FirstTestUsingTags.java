package com.learning.playwright.InitialTests;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class FirstTestUsingTags extends Baseclass {

    @Test
    public void checkPageTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();
        System.out.println(title);
        Assertions.assertTrue(title.equals("Practice Software Testing - Toolshop - v5.0"));
    }


    @Test
    public void searchForElement(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
//        page.locator("[placeholder=Search]").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator("[placeholder='Search']").waitFor();
        page.locator("[placeholder='Search']").fill("Pliers");
        page.locator("button:has-text('Search')").click();
        int count = page.locator("(//div[@class='container'])[3]/child::a").count();
        System.out.println("The Total Count for Search Results : " + count);
        Assertions.assertTrue(count > 0);
    }
}
