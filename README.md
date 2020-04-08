# Projet bomberman

# Setup BDD:

MYSQL_USERNAME=root	#user mysql
PASSWORD_USERNAME=root	#password mysql

MYSQL_USER=bomberman
MYSQL_PASSWORD=bomberman
DATABASE=bomberman_db

mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "CREATE DATABASE $DATABASE"
mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD'"
mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "GRANT ALL ON $DATABASE.* TO '$MYSQL_USER'@'%'"

# Configuration et lancement
Le serveur web utilise le port 8080 (non paramétrable #todo)
Le serveur de jeu utilise le port 8090 (non paramétrable #todo)

Les deux serveurs (BombermanWeb & BombermanServer) doivent pour le moment êtres sur la même ip. (#todo possibilité de configurer ce point)

Le site web est accessible à l'adresse: http://[ip_serveur_web_sans_port]:8080/bomberman

Il est nécessaire de créer un utilisateur "bomberman" qui sera l'administrateur du jeu avant de pouvoir lancer le serveur de jeu.

Le serveur nécessite l'utilisateur "bomberman" pour effectuer les appels à l'api de statistiques en fin de partie. Le mot de passe est transmis au serveur à son lancement via la ligne de commande:
java -jar BombermanServer.jar [ip_serveur_web_sans_port] [password_user_bomberman]

Démarrage du client:
java -jar BombermanClient.jar
Le client devra rentrer l'adresse du serveur web afin de se connecter au serveur de jeu avec ses identifiant.

