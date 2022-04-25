FROM adoptopenjdk/openjdk16

ADD build/libs/app.jar /play-together/app.jar

EXPOSE 8080

ENTRYPOINT java -jar /play-together/app.jar