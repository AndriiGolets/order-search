# start with Tomcat
FROM openjdk:17

# arg name matches the docker maven plugin buildArgs
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/viber-bot.jar

ENTRYPOINT [ "java", "-jar", "/usr/share/myservice/viber-bot.jar" ]