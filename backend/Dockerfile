#
# Build stage
#
FROM maven:3.8.6-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
# Replace the local persistence config with one made for docker
RUN mv -f /home/app/src/main/resources/hibernate.cfg-docker.xml /home/app/src/main/resources/hibernate.cfg.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM quay.io/wildfly/wildfly
COPY --from=build /home/app/target/ap-backend.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]