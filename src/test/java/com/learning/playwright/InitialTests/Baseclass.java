package com.learning.playwright.InitialTests;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;

import java.util.Arrays;

@UsePlaywright(Baseclass.CustomOptions.class)
public class Baseclass {

    public static class CustomOptions implements OptionsFactory{

        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(false)
                    .setLaunchOptions(new BrowserType.LaunchOptions()
                            .setArgs(Arrays.asList
                                    ("--no-sandbox","--disable-extensions","--disable-gpu","--start-maximized")));
        }
    }
}
