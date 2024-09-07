# 로그인 계정(username: foo, pw: foo)
INSERT IGNORE INTO growing.account (id, is_super_user, password, user_id, username) VALUES (1, true, '$2a$10$r0v2AxWht/yfX6XcTtgneOPE3uVI7cjHr51k1GyvG747n0uDCcIMa', 1, 'foo');

# 텀
INSERT IGNORE INTO growing.term (id, is_active) VALUES (1, true);

# 순모임 + 순모임 순장
INSERT IGNORE INTO growing.small_group (id, created_at, created_by, updated_at, updated_by, small_group_leader_id, term_id) VALUES (1, '2024-06-06 13:50:18', null, '2024-06-06 13:50:21', null, 1, 1);
INSERT IGNORE INTO growing.small_group_leader (id, created_at, created_by, updated_at, updated_by, term_id, user_id) VALUES (1, '2024-06-19 19:49:36', null, '2024-06-19 19:49:38', null, 1, 1);
INSERT IGNORE INTO growing.user (id, created_at, created_by, updated_at, updated_by, birth, sex, grade, name, phone_number) VALUES (1, '2024-06-18 19:38:34.251207', null, '2024-06-18 19:38:34.251207', null, '2000-10-16', 'MALE', 9, '일반순장이름', '010-1234-5678');

# 새가족반 + 새가족 순장
INSERT IGNORE INTO growing.new_family_group (id, created_at, created_by, updated_at, updated_by, term_id, new_family_group_leader_id) VALUES (1, '2024-06-06 14:34:09', null, '2024-06-06 14:34:10', null, 1, 1);
INSERT IGNORE INTO growing.new_family_group_leader (id, created_at, created_by, updated_at, updated_by, term_id, user_id) VALUES (1, '2024-06-18 19:41:04', null, '2024-06-18 19:41:06', null, 1, 2);
INSERT IGNORE INTO growing.user (id, created_at, created_by, updated_at, updated_by, birth, sex, grade, name, phone_number) VALUES (2, '2024-06-18 19:38:34.251207', null, '2024-06-18 19:38:34.251207', null, '2000-10-16', 'MALE', 9, '새가족순장이름', '010-1234-5678');

## 새가족반에 배정된 새가족
INSERT INTO growing.new_family (id, created_at, created_by, updated_at, updated_by, etc, new_family_group_id, new_family_promote_log_id, small_group_id, user_id, visit_date) VALUES (1, '2024-06-19 20:09:51.054731', null, '2024-06-19 20:09:51.054731', null, '{"school": "서울대학교 감자학과 6학년", "comment": "이 친구는 짜장면을 좋아합니다", "introducer": "박똘똘", "visitReason": "INTRODUCE", "latestChurch": "온누리교회", "isFirstChurch": false, "relationshipWithJesus": "NONE", "hasCertaintityOfSalvation": false}', 1, null, null, 3, '2024-06-01');
INSERT INTO growing.user (id, created_at, created_by, updated_at, updated_by, birth, sex, grade, name, phone_number) VALUES (3, '2024-06-19 20:09:51.026792', null, '2024-06-19 20:09:51.026792', null, '2000-10-16', 'MALE', 9, '새가족1', '010-1234-5678');

# 일반 순모임에 배정된 새가족
INSERT INTO growing.user (id, created_at, created_by, updated_at, updated_by, birth, sex, grade, name, phone_number) VALUES (4, '2024-06-19 20:10:14.036236', null, '2024-06-19 20:10:14.036236', null, '2000-10-16', 'MALE', 9, '새가족2', '010-1234-5678');
INSERT INTO growing.new_family (id, created_at, created_by, updated_at, updated_by, etc, new_family_group_id, new_family_promote_log_id, small_group_id, user_id, visit_date) VALUES (2, '2024-06-19 20:10:14.041425', null, '2024-06-19 20:10:22.225398', null, '{"school": "서울대학교 감자학과 6학년", "comment": "이 친구는 짜장면을 좋아합니다", "introducer": "박똘똘", "visitReason": "INTRODUCE", "latestChurch": "온누리교회", "isFirstChurch": false, "relationshipWithJesus": "NONE", "hasCertaintityOfSalvation": false}', null, null, 1, 4, '2024-06-01');
