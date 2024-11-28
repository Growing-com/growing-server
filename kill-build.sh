echo "🌈 1. 소스 코드를 받아옵니다."

git pull

echo "🌈 2. SpringBoot 프로젝트 빌드를 시작합니다."

./gradlew clean
./gradlew build -x test -x asciidoctor

CURRENT_PID=$(pgrep -f ./*.jar | head -n 1)

if [ -z "$CURRENT_PID" ]; then
    echo "🌈 3. 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "🌈 3. 구동중인 애플리케이션을 종료했습니다. (pid : $CURRENT_PID)"
    kill -9 $CURRENT_PID
fi
