FROM tomcat:8.5.55-jdk11-openjdk AS BUILDER_IMAGE
RUN mkdir -p /app/source
ARG env
ARG url
ARG user
ARG pass
ARG port
ENV SPRING_PROFILES_ACTIVE=${env}
ENV DB_URL=${db_url}
ENV DB_USERNAME=${db_user}
ENV DB_PASSWORD=${db_pass}
ENV TOMCAT_PORT=${tomcat_port}
COPY . /app/source
WORKDIR /app/source
RUN apt-get update -y && apt-get upgrade -y && apt-get clean && rm -rf /var/lib/apt/lists/*


FROM tomcat:8.5.55-jdk11-openjdk-slim
WORKDIR /app
COPY --from=BUILDER_IMAGE /app/source/drop/build/libs/simple-*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
