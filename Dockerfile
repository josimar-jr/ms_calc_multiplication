# configures the builder container
FROM gradle:6.6-jdk8 as builder

WORKDIR /ms-multi/

COPY . .

RUN ./gradlew build

RUN ls -lha /ms-multi/build/libs

FROM openjdk:14-alpine as runner

ARG APP_VERSION=0.1.0

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /ms-multi

RUN chown -R $APPLICATION_USER /ms-multi

USER $APPLICATION_USER

WORKDIR /ms-multi
COPY --from=builder /ms-multi/build/libs/msmulti-${APP_VERSION}.jar /ms-multi/application.jar

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "application.jar"]
