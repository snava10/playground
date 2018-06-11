package com.shop.services.products;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> fut) {
        vertx.createHttpServer().requestHandler(r -> {
            r.response().end("<h1>Products services, powered by Vert.x 3</h1>");
        }).listen(config().getInteger("http.port", 8080),
                result -> {
                    if (result.succeeded()) {
                        System.out.println("Succeeded");
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }
}
