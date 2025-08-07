package com.learning.playwright.InitialTests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

@UsePlaywright(Baseclass.CustomOptions.class)
public class FirstTestUsingTags{

    @Test
    public void checkPageTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();
        System.out.println(title);
        Assertions.assertThat(title.equals("Practice Software Testing - Toolshop - v5.0"));
    }


    @Test
    public void searchForElement(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
//        page.locator("[placeholder=Search]").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Search")).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        List<Double> prices = page.getByTestId("product-price").allInnerTexts().stream().map(price -> Double.parseDouble(price.replace("$",""))).toList();
        System.out.println(prices);
        Assertions.assertThat(prices)
                .allMatch(price -> price> 0)
                .doesNotContain(0.0)
                .allMatch(price -> price < 1000)
                .allSatisfy(price ->
                        Assertions.assertThat(price)
                                .isGreaterThan(0.0)
                                .isLessThan(1000.0));
    }


    @Test
    public void sortAndSearch(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByLabel("Sort").selectOption("Name (Z - A)");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        List<String> productNames = page.getByTestId("product-name").allTextContents();
        Assertions.assertThat(productNames).isSortedAccordingTo(Comparator.reverseOrder());

    }
}
