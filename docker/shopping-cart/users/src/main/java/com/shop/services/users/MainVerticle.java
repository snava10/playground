package com.shop.services.users;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> fut) {
        vertx.createHttpServer().requestHandler(r -> {
            r.response().end("<h1>Users service, powered by Vert.x 3</h1>");
        }).listen(4001, result -> {
            if (result.succeeded()) {
                fut.complete();
            } else {
                fut.fail(result.cause());
            }
        });
    }
}
