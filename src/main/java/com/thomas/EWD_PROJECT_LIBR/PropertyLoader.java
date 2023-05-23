package com.thomas.EWD_PROJECT_LIBR;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyLoader {

    private static Locale locale = new Locale("en");

    public static String getProperty(String key) {
        return ResourceBundle.getBundle("messages", locale).getString(key);
    }

    public static void setLocale(String language) {
        locale = new Locale(language);
    }

    public static String getLocale() {
        return locale.getLanguage();
    }

    public static void changeLocale() {
        if (locale.getLanguage().equals("en")) {
            locale = new Locale("nl");
        } else {
            locale = new Locale("en");
        }
    }

}
