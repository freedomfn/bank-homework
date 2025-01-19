FROM bitnami/java:21.0.5-11
ADD app.jar /app
WORKDIR /app
EXPOSE 80
CMD java -jar $JAVA_OPTS app.jar