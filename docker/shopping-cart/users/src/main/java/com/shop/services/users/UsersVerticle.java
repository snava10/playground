package com.shop.services.users;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class UsersVerticle extends AbstractVerticle {

    ServiceDiscovery discovery;
    EventBus eventBus;

    @Override
    public void start(Future<Void> fut) {

        eventBus = vertx.eventBus();
        discovery = ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions()
                .setAnnounceAddress("service-announce")
                .setName("users"));

        vertx.createHttpServer().requestHandler(r -> {
            r.response().end("<h1>Users service, powered by Vert.x 3</h1>");
        }).listen(8080, result -> {
            if (result.succeeded()) {
                fut.complete();
            } else {
                fut.fail(result.cause());
            }
        });

        // Record creation from a type
        Record record = HttpEndpoint.createRecord("users-rest-api", "localhost", 8080, "/api");

        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                // publication succeeded
                Record publishedRecord = ar.result();
                System.out.println("Published: " + publishedRecord);
            } else {
                // publication failed
            }
        });

        discover(discovery);

        //Event bus
        eventBus.consumer("com.shop", message -> {
            System.out.println("I have received a message: " + message.body());
        });

        sendMessage();
    }

    @Override
    public void stop(Future<Void> fut) {
        if (discovery != null)
            discovery.close();
    }

    private void discover(ServiceDiscovery discovery) {
        // Get a record by name
        discovery.getRecord(r -> r.getName().equals("products"), ar -> {
            if (ar.succeeded()) {
                if (ar.result() != null) {
                    // we have a record
                    System.out.println(ar.result());
                } else {
                    // the lookup succeeded, but no matching service
                    System.out.println("No services found");
                }
            } else {
                // lookup failed
                System.out.println("Lookup failed");
            }
        });
    }

    private void sendMessage() {
        vertx.setPeriodic(10000, v -> eventBus.publish("com.shop", "Some news from the Users service!"));
    }
}
