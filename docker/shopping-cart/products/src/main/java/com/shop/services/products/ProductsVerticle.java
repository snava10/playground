package com.shop.services.products;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class ProductsVerticle extends AbstractVerticle {

    ServiceDiscovery discovery;
    EventBus eventBus;

    @Override
    public void start(Future<Void> fut) {

        eventBus = vertx.eventBus();
        discovery = ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions()
                .setAnnounceAddress("service-announce")
                .setName("products"));

        int port = config().getInteger("http.port");
        String html = "<h1>Products services, powered by Vert.x 3</h1>";
        vertx.createHttpServer().requestHandler(r -> r.response().end(html)).listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Succeeded");
                fut.complete();
            } else {
                fut.fail(result.cause());
            }
        });

        // Record creation from a type
        Record record = HttpEndpoint.createRecord("products-rest-api", "localhost", port, "/api");

        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                // publication succeeded
                Record publishedRecord = ar.result();
                System.out.println("Published: " + publishedRecord);
            } else {
                // publication failed
                System.out.println("Publication failed");
            }
        });

        discover(discovery);

        //Event bus
        eventBus.consumer("com.shop", message -> {
            System.out.println("I have received a message: " + message.body());
        });

    }

    @Override
    public void stop(Future<Void> fut) {
        if (discovery != null)
            discovery.close();
    }

    private void discover(ServiceDiscovery discovery) {
        // Get a record by name
        discovery.getRecord((JsonObject) null, ar -> {
            if (ar.succeeded()) {
                if (ar.result() != null) {
                    // we have a record
                    System.out.println("Found: " + ar.result());
                } else {
                    // the lookup succeeded, but no matching service
                    System.out.println("No services found");
                }
            } else {
                // lookup failed
            }
        });
    }
}
