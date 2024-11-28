echo "🌈 4. SpringBoot 애플리케이션을 실행합니다."

JAR_PATH=$(ls ./build/libs | grep .jar | head -n 1)
nohup java -jar -Dspring.profiles.active=prod ./build/libs/$JAR_PATH &
