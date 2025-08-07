package com.learning.playwright.Locators;


import com.learning.playwright.InitialTests.Baseclass;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@UsePlaywright(Baseclass.CustomOptions.class)
public class FormsHandling {

    @DisplayName("Fill the Contact Form")
    @Test
    public void fillContactForm(Page page) throws URISyntaxException {
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByRole(AriaRole.MENUBAR,new Page.GetByRoleOptions().setName("Main menu"))
                .getByText("Contact")
                .click();
        var firstName = page.getByLabel("First name");
        var lastName = page.getByLabel("Last name");
        var message = page.getByLabel("Message *");
        var upload = page.locator("#attachment");
        firstName.fill("Koushick");
        lastName.fill("Sudharsanam");
        page.getByTestId("email").fill("test@test.com");
        var optionVal = page.locator("#subject");

        optionVal.selectOption("Return");

        message.fill("Hello, World!");

        Path filetoUpload = Paths.get(ClassLoader.getSystemResource("data/Sample-file.txt").toURI());

        page.setInputFiles("#attachment",filetoUpload);

        assertThat(firstName).hasValue("Koushick");
        assertThat(lastName).hasValue("Sudharsanam");
        assertThat(page.getByTestId("email")).hasValue("test@test.com");
        assertThat(optionVal).hasValue("return");
        assertThat(message).hasValue("Hello, World!");

        String UploadedFile = upload.inputValue();

        Assertions.assertThat(UploadedFile).endsWith("Sample-file.txt");


    }

    @DisplayName("Check Mandatory Messages Fields")
    @ParameterizedTest
    @ValueSource(strings = {"First name","Last name","Email","Message"})
    public void mandatoryFieldsInContactForm(String fieldName,Page page) throws URISyntaxException {
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByRole(AriaRole.MENUBAR,new Page.GetByRoleOptions().setName("Main menu"))
                .getByText("Contact")
                .click();
        var firstName = page.getByLabel("First name");
        var lastName = page.getByLabel("Last name");
        var message = page.getByLabel("Message *");
        var email = page.getByLabel("Email");
        var submitBtn = page.getByText("Send");
        firstName.fill("Koushick");
        lastName.fill("Sudharsanam");
        email.fill("test@test.com");
        var optionVal = page.locator("#subject");

        optionVal.selectOption("Return");

        Path filetoUpload = Paths.get(ClassLoader.getSystemResource("data/Sample-file.txt").toURI());

        page.setInputFiles("#attachment",filetoUpload);

        page.getByLabel(fieldName).clear();

        submitBtn.click();

        Locator allMandatoryfields = page.getByRole(AriaRole.ALERT)
                .getByText(fieldName+" is required");

        PlaywrightAssertions.assertThat(allMandatoryfields).isVisible();
        System.out.println(allMandatoryfields.textContent());
    }
}
