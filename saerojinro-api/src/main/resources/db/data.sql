-- 예시 사용자 데이터 삽입
INSERT INTO "user" (id, oauth_identity, email, password, profile_image, name, role, provider, interest)
VALUES
    (10001, 'oauth1', 'user1@example.com', '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', 'profile1.png', 'Alice', 'ATTENDEE', 'GOOGLE', 'BACKEND'),
    (10002, 'oauth2', 'user2@example.com', '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', 'profile2.png', 'Bob', 'SPEAKER', 'KAKAO', 'FRONTEND'),
    (10003, 'oauth3', 'alswns11346@kgu.ac.kr', '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', 'profile3.png', 'Charlie', 'ADMIN', 'GOOGLE', 'AI');

-- 예시 강연 데이터 삽입 (speaker_id는 user 테이블의 id 참조)
INSERT INTO lecture (id, speaker_id, title, contents, max_capacity, start_time, end_time, location, category, lecture_status)
VALUES
    (10001, 10002, 'Introduction to Backend', 'Learn backend basics', 100, '2025-04-01 10:00:00', '2025-04-01 12:00:00', 'Room 101', 'BACKEND', 'APPROVED'),
    (10002, 10002, 'Advanced Frontend', 'Deep dive into frontend technologies', 50, '2025-05-01 14:00:00', '2025-05-01 16:00:00', 'Room 202', 'FRONTEND', 'PENDING_APPROVAL');

-- 예시 알림 데이터 삽입
INSERT INTO notification (id, user_id, title, contents)
VALUES
    (10001, 10001, 'Welcome', 'Welcome to the system!'),
    (10002, 10002, 'Lecture Approved', 'Your lecture has been approved.');

-- 예시 예약 데이터 삽입
INSERT INTO reservation (id, user_id, lecture_id)
VALUES
    (10001, 10001, 10001),
    (10002, 10003, 10002);

-- 예시 리뷰 데이터 삽입
INSERT INTO review (id, user_id, lecture_id, content, rating)
VALUES
    (10001, 10001, 10001, 'Great lecture!', 4.5),
    (10002, 10001, 10002, 'Informative session', 4.0);

-- 예시 위시리스트 데이터 삽입
INSERT INTO wish_list (id, user_id, lecture_id)
VALUES
    (10001, 10001, 10002),
    (10002, 10001, 10001);
