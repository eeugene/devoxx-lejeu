FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD hero-api-0.0.1-SNAPSHOT.jar hero.jar
RUN bash -c 'touch /hero.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/hero.jar"]