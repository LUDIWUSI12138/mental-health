# 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# 限制构建堆内存，适配 Render 免费实例
ENV MAVEN_OPTS="-Xmx350m"
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline || true
COPY src ./src
RUN mvn -B clean package -DskipTests

# 运行阶段
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/mental-health-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
