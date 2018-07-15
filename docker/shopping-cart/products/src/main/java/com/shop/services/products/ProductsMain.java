package com.shop.services.products;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

import java.util.function.Consumer;

public class ProductsMain {

    public static void main(String[] args) {

        String localIp = System.getProperty("local.ip");
        String publicIp = System.getProperty("public.ip");
        System.out.println(String.format("local.ip: %s\npublic.ip: %s", localIp, publicIp));

        System.out.println("Configuring hazelcast");
        Config hazelcastConfig = new Config();
        hazelcastConfig.setProperty("hazelcast.local.localAddress", localIp);
        NetworkConfig networkConfig = hazelcastConfig.getNetworkConfig();
        InterfacesConfig networkInterface = networkConfig.getInterfaces();
        networkInterface.setEnabled(true).addInterface(localIp);
        networkConfig.setPublicAddress(publicIp);

        ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);

        System.out.println("Configuring vertx");

        VertxOptions vertxOptions = new VertxOptions().setClustered(true)
                .setClusterManager(mgr)
                .setClusterHost(localIp)
                .setClusterPort(40404)
                .setEventBusOptions(new EventBusOptions().setPort(40933));
        DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8081));

        run(ProductsVerticle.class.getName(), vertxOptions, options);
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
