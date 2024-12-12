package config;

import org.aeonbits.owner.ConfigFactory;

public class ProjectConfig {


    public static String getBaseUrl(){
        return ConfigLoader.getProperty("base.url");
    }

    public static  String getCucumberFilterTags(){
        return ConfigLoader.getProperty("cucumber.filter.tags");
    }

    public static  String getTestEnv(){
        return  ConfigLoader.getProperty("test.env");
    }

    public static String getSelenoidUrl(){
        return ConfigLoader.getProperty("selenoid.url");
    }
}
