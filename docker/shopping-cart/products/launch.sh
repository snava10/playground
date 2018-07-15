#!/usr/bin/env bash

MY_LOCAL=`ip addr | grep 172 | awk '{print $2}' | cut -d/ -f1`
MY_PUBLIC=`ip addr | grep 10.42 | awk '{print $2}' | cut -d/ -f1`
echo ${MY_LOCAL}
echo ${VERTICLE_HOME}
java -agentlib:jdwp=transport=dt_socket,server=y,address=50501,suspend=n -jar -Dlocal.ip=${MY_LOCAL} -Dpublic.ip=${MY_PUBLIC} ${VERTICLE_HOME}/products-3.5.1-fat.jar -conf products-conf.json
