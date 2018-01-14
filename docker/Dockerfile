FROM ${DOCKER_REGISTRY}/jre8
WORKDIR /app
EXPOSE 8080
COPY ${PROJECT_NAME}.jar /app/
COPY start.sh /app/
RUN \
    chmod +x /app/start.sh
CMD ["/app/start.sh","${PROJECT_NAME}.jar"]