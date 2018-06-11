###
# vert.x docker example using a Java verticle
# To build:
#  docker build -t sample/vertx-java .
# To run:
#   docker run -t -i -p 8080:8080 sample/vertx-java
###

# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME com.shop.services.products.MainVerticle
#ENV VERTICLE_FILE target/products-3.5.1.jar
ENV VERTICLE_FILE target/products-3.5.1-fat.jar
ENV CONFIG_FILE src/main/conf/products-conf.json

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080 8081 50505

# Copy your verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/
COPY $CONFIG_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
#CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
CMD ["java -agentlib:jdwp=transport=dt_socket,server=y,address=50505,suspend=n -jar $VERTICLE_HOME/products-3.5.1-fat.jar -conf products-conf.json"]
