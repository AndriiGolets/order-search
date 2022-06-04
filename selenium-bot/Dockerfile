# start with Tomcat
FROM openjdk:17

# arg name matches the docker maven plugin buildArgs
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/selenium-bot.jar

ENTRYPOINT [ "java", "-jar", "/usr/share/myservice/selenium-bot.jar" ]
