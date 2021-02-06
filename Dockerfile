FROM openjdk:8-jre

MAINTAINER ovidiu.simionica


ARG build_version=0.0.8-SNAPSHOT

ENV VERSION=${build_version}
ENV SPRING_PROFILE=""
ENV SPRING_ARGS=""
ENV JAVA_OPTS=-Xmx512M

EXPOSE 8080

ADD target/template-spring-service-${build_version}.jar template-spring-service-${build_version}.jar

ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILE} -jar template-spring-service-${VERSION}.jar --server.port=8080 $SPRING_ARGS
