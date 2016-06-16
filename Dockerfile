FROM java:8-jdk
MAINTAINER Pedro F. Alonso <pedro.alonso.garcia@accenture.com>
USER root
VOLUME /tmp
ADD producerid-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 1111
ENTRYPOINT ["java","-jar","/app.jar"]
