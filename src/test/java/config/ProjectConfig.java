package config;

import org.aeonbits.owner.ConfigFactory;

public class ProjectConfig {

    private static final EnvConf config = ConfigFactory.create(EnvConf.class);

    public static String getBaseUrl(){
        return config.baseUrl();
    }

    public static  String getCucumberFilterTags(){
        return config.cucumberFilterTags();
    }

    public static  String getTestEnv(){
        return  config.testEnv();
    }

    public static String getSelenoidUrl(){
        return config.selenoidUrl();
    }
}
