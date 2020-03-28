FROM openjdk:8-jre-alpine

ARG DEBIAN_FRONTEND=noninteractive

RUN apt update &&\
	apt install mysql-server

COPY BombermanServer.war/ /bomberman
COPY BombermanWeb.war/ /bomberman
COPY start.sh /bomberman

WORKDIR /bomberman

## Setup de la base de données
ENV MYSQL_PASSWORD=bomberman \
	MYSQL_USER=bomberman \
	DATABASE=bomberman_db

RUN service mysql start &&\
	mysql -y root --execute="CREATE DATABASE $DATABASE" &&\
	mysql -u root --execute="CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD'" &&\
   	mysql -u root --execute="GRANT ALL ON $DATABASE.* TO '$MYSQL_USER'@'%'" &&\

EXPOSE 8080
EXPOSE 8090

ENTRYPOINT ["/bin/bash","-c","/start.sh"]
