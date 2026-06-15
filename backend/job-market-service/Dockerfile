# SkillDNA AI — Ravi Future Labs
# Multi-stage Docker build for job-market-service

# ── Stage 1: Build ──────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN apk add --no-cache maven &&     mvn package -DskipTests -q

# ── Stage 2: Runtime ────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Non-root user for security
RUN addgroup -S skilldna && adduser -S skilldna -G skilldna
USER skilldna

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

LABEL org.opencontainers.image.title="SkillDNA AI - job-market-service"       org.opencontainers.image.vendor="Ravi Future Labs"       org.opencontainers.image.version="1.0.0"       org.opencontainers.image.authors="Ravikumar"
