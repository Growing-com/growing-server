CURRENT_PID=$(pgrep -f ./*.jar | head -n 1)

if [ -z "$CURRENT_PID" ]; then
    echo "🌈 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "🌈 구동중인 애플리케이션을 종료했습니다. (pid : $CURRENT_PID)"
    kill -9 $CURRENT_PID
fi

echo "🌈 SpringBoot 프로젝트 빌드를 시작합니다."

./gradlew clean
./gradlew build -x test -x asciidoctor

echo "🌈 SpringBoot 애플리케이션을 실행합니다."

JAR_PATH=$(ls ./build/libs | grep .jar | head -n 1)
nohup java -jar -Dspring.profiles.active=prod ./build/libs/$JAR_PATH &
