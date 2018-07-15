package com.shop.services.users;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;

public class UsersMain {

    public static void main(String[] args){

        VertxOptions vertxOptions = new VertxOptions().setClustered(true)
                .setEventBusOptions(new EventBusOptions()
                .setPort(40933));
        DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8080));
        run(UsersVerticle.class.getName(), vertxOptions, deploymentOptions);
    }

    private static void run(String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
        if (options == null) {
            // Default parameter
            options = new VertxOptions();
        }
        // Smart cwd detection

        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticleID, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticleID);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                    res.cause().printStackTrace();
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
}
