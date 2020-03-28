echo "BombermanWeb starting..."
usr/bin/java -jar -Dspring.profiles.active=default /BombermanWeb.war
echo "BombermanWeb successfully started."

echo "BombermanServer starting..."
usr/bin/java -jar -Dspring.profiles.active=default /BombermanServer.war
echo "BombermanServer successfully started."
