FROM adoptopenjdk/openjdk16

ADD app/build/libs/app.jar /play-together/app.jar

EXPOSE 8080

ENTRYPOINT java -jar /play-together/app.jar