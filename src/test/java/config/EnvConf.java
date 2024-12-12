package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:conf.properties")
public interface EnvConf  extends Config {

    @Key("db.login")
    String username();

    @Key("db.password")
    String password();

    @Key("db.url")
    String dbUrl();

    @Key("base.url")
    String baseUrl();
}
