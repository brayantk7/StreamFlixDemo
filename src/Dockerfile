FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY src/ /app/src/
RUN cd src && javac StreamFlixServer.java models/*.java core/*.java
CMD ["java", "-cp", "src", "StreamFlixServer"]