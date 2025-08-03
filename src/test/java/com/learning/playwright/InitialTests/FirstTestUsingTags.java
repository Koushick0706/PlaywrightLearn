package com.learning.playwright.InitialTests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@UsePlaywright(Baseclass.CustomOptions.class)
public class FirstTestUsingTags{

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
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Search")).click();
        Locator count = page.locator(".card");
        System.out.println("The Total Count for Search Results : " + count.allTextContents());
        PlaywrightAssertions.assertThat(count).hasCount(4);
    }
}
