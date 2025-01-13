echo "🌈 1. master 브랜치를 받아옵니다."

git add .
git stash
git checkout master
git pull

echo "🌈 2. SpringBoot 프로젝트 빌드를 시작합니다."

./gradlew clean
./gradlew build -x test -x asciidoctor

CURRENT_PID=$(pgrep -f ./*.jar | head -n 1)

if [ -z "$CURRENT_PID" ]; then
    echo "🌈 3. 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "🌈 3. 구동중인 애플리케이션을 종료했습니다. (pid : $CURRENT_PID)"
    kill -15 $CURRENT_PID
fi

CURRENT_PID2=$(pgrep -f ./*.jar | head -n 1)

if [ -z "$CURRENT_PID2" ]; then
    echo "🌈 3. 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "🌈 3. 구동중인 애플리케이션을 종료했습니다. (pid : $CURRENT_PID2)"
    kill -15 $CURRENT_PID2
fi

echo "🌈 4. SpringBoot 애플리케이션을 실행합니다."

JAR_PATH=$(ls ./build/libs | grep .jar | head -n 1)
nohup java -jar -Dspring.profiles.active=prod ./build/libs/$JAR_PATH &
