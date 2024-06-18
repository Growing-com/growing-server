INSERT INTO growing.term (id, is_active) VALUES (1, true);
INSERT INTO growing.small_group (id, created_at, created_by, updated_at, updated_by, small_group_leader_id, term_id) VALUES (1, '2024-06-06 13:50:18', null, '2024-06-06 13:50:21', null, 1, 1);

INSERT INTO growing.account (id, is_super_user, password, user_id, username) VALUES (1, true, '$2a$10$r0v2AxWht/yfX6XcTtgneOPE3uVI7cjHr51k1GyvG747n0uDCcIMa', 1, 'foo');
INSERT INTO growing.user (id, created_at, created_by, updated_at, updated_by, birth, gender, grade, name, phone_number) VALUES (1, '2024-06-18 19:38:34.251207', null, '2024-06-18 19:38:34.251207', null, '2000-10-16', 'MALE', 9, '김똘똘', '010-1234-5678');

INSERT INTO growing.new_family_group (id, created_at, created_by, updated_at, updated_by, term_id, new_family_group_leader_id) VALUES (1, '2024-06-06 14:34:09', null, '2024-06-06 14:34:10', null, 1, 1);
INSERT INTO growing.new_family_group_leader (id, created_at, created_by, updated_at, updated_by, term_id, user_id) VALUES (1, '2024-06-18 19:41:04', null, '2024-06-18 19:41:06', null, 1, 1);
