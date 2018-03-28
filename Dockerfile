FROM gitlab.blockshine.net:4567/library/java:8-openjdk

ADD build/libs/open-api.jar /usr/src/open-api.jar

WORKDIR /usr/src

CMD ["java", "-Duser.timezone=Asia/Shanghai", "-jar", "open-api.jar"]
