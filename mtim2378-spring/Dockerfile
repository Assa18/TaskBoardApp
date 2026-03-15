# ---------------------------------------------------
# Phase 1: compile and prepare distributable
# ---------------------------------------------------

FROM gradle:8.4-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY mtim2378-spring/build.gradle ./mtim2378-spring/build.gradle

RUN gradle :mtim2378-spring:dependencies --no-daemon

COPY mtim2378-spring/src/main ./mtim2378-spring/src/main
RUN gradle :mtim2378-spring:bootJar --no-daemon

# ---------------------------------------------------
# Phase 2: copy the distributable and run it in a JRE
# ---------------------------------------------------

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/mtim2378-spring/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
