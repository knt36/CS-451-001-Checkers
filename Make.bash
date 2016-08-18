JC="javac"
JVM="java"
if [ "$1" = "server" ]
    then
        "$JC" -cp lib/gson-2.7.jar:src -d out src/main/ServerMain.java
        "$JVM" -cp lib/gson-2.7.jar:src:out main.ServerMain
elif [ "$1" == "client" ]
    then
    "$JC" -cp lib/gson-2.7.jar:src -d out src/main/ClientMain.java
    "$JVM" -cp lib/gson-2.7.jar:src:out main.ClientMain
else
    echo "Call this with client or server!"
fi
