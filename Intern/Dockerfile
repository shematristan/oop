FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./src /app

RUN javac -d . *.java

CMD ["java", "TaxSystem"]
