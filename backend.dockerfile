

FROM openjdk:17-ea-jdk-alpine as build
WORKDIR /app
COPY ./backend/ .
RUN apk add --no-cache maven && mvn clean package -DskipTests

FROM openjdk:17-ea-jdk-alpine as run
RUN addgroup --system spring && adduser --system spring --ingroup spring
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar"]
