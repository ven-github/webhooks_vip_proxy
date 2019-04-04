package com.ven.social;

import com.ven.social.resources.FacebookPageResource;
import com.ven.social.resources.InstagramResource;
import com.ven.social.resources.TwitterResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App extends Application<AppConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "Webhooks Vip Proxy";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bs) {

    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment e) throws Exception {

        logger.info("Webhooks Vip Proxy application ..");

        e.jersey().register(new TwitterResource(appConfiguration.getBufferSize()));
        e.jersey().register(new InstagramResource(appConfiguration.getBufferSize()));
        e.jersey().register(new FacebookPageResource(appConfiguration.getBufferSize()));

        logger.info("Registering HealthCheck App");
        e.healthChecks().register("app", new AppHealthCheck());
    }
}