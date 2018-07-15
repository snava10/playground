package com.shop.services.products;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

//@RunWith(VertxUnitRunner.class)
public class ProductsVerticleTest {

    private Vertx vertx;
    private int port;

//    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();
        DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));
        vertx.deployVerticle(ProductsVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

//    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

//    @Test
    public void startTest(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/", response -> {
            response.handler(body -> {
                context.assertTrue(body.toString().contains("Products"));
                async.complete();
            });
        });
    }
}