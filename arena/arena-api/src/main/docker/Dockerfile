FROM java:8
VOLUME /tmp
EXPOSE 8082
ADD arena-api-0.0.1-SNAPSHOT.jar arena.jar
RUN bash -c 'touch /arena.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/arena.jar"]