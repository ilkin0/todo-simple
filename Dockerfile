FROM tomcat:8.5.55-jdk11-openjdk
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
COPY drop/build/libs/simple-*.war /usr/local/tomcat/webapps/ROOT.war
RUN sudo apt-get update && sudo apt-get upgrade && sudo apt-get clean
RUN mkdir /usr/local/tomcat/files
EXPOSE 8080