###
# vert.x docker example using a Java verticle
# To build:
#  docker build -t sample/vertx-java .
# To run:
#   docker run -t -i -p 8080:8080 sample/vertx-java
###

# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME com.shop.services.products.ProductsVerticle
#ENV VERTICLE_FILE target/products-3.5.1.jar
ENV VERTICLE_FILE target/products-3.5.1-fat.jar
ENV CONFIG_FILE src/main/conf/products-conf.json

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8081 50501 40933 40404

# Copy your verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/
COPY $CONFIG_FILE $VERTICLE_HOME/
COPY launch.sh $VERTICLE_HOME/


# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]

CMD ["chmod +x ${VERTICLE_HOME}/launch.sh; cd ${VERTICLE_HOME}; ./launch.sh"]

#CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
#CMD ["MY_LOCAL=`ip addr | grep 172 | awk '{print $2}' | cut -d/ -f1`"]
#CMD ["MY_PUBLIC=`ip addr | grep 10.42 | awk '{print $2}' | cut -d/ -f1`"]
#CMD ["java -agentlib:jdwp=transport=dt_socket,server=y,address=50501,suspend=n -jar -Dlocal.ip=$MY_LOCAL -Dpublic.ip=$MY_PUBLIC $VERTICLE_HOME/products-3.5.1-fat.jar -conf products-conf.json"]
