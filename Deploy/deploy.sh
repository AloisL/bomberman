MYSQL_USERNAME=root	#user mysql
PASSWORD_USERNAME=root	#password mysql

MYSQL_USER=bomberman
MYSQL_PASSWORD=bomberman
DATABASE=bomberman_db

mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "CREATE DATABASE $DATABASE"
mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD'"
mysql -u$MYSQL_USERNAME -p$PASSWORD_USERNAME -e "GRANT ALL ON $DATABASE.* TO '$MYSQL_USER'@'%'"