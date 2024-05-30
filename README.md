### 0. Guide

---

1. db 실행 - docker-compose -f docker-compose-local.yml up -d
2. db 접속 - DB client 를 통해 localhost:3307/growing 접속 (id: root, password: root)
3. sql 실행 - SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
4. sql 실행 - dump.sql
5. src/main/resources 에 application.yml, logback.yml 파일 생성
6. 애플리케이션 실행
7. 애플리케이션 종료
8. db 종료 - docker-compose -f docker-compose-local.yml down

### 1. Discussions

---

* TBD

### 2. Todos

---

* Batch 테스트

### 3. Backlogs

---

* 새가족 정착률 조사 시트
* 출석 미완료 인원 목록
* 라인아웃 인원 목록
