---------------------------
-- 1. file 테이블 (총 10건)
---------------------------
insert into file (id, created_at, updated_at, deleted_at, file_size, extension, logical_name, physical_path)
values
    (1001, current_timestamp, current_timestamp, null, 1024, 'jpg', 'image1', '/files/image1.jpg'),
    (1002, current_timestamp, current_timestamp, null, 2048, 'pdf', 'lecture_material1', '/files/lecture_material1.pdf'),
    (1003, current_timestamp, current_timestamp, null, 512,  'png', 'lecture_thumbnail1', '/files/lecture_thumbnail1.png'),
    (1004, current_timestamp, current_timestamp, null, 1024, 'jpg', 'speaker_image1', '/files/speaker_image1.jpg'),
    (1005, current_timestamp, current_timestamp, null, 2048, 'jpg', 'speaker_image2', '/files/speaker_image2.jpg'),
    (1006, current_timestamp, current_timestamp, null, 1024, 'png', 'speaker_image3', '/files/speaker_image3.png'),
    (1007, current_timestamp, current_timestamp, null, 3072, 'pdf', 'lecture_material2', '/files/lecture_material2.pdf'),
    (1008, current_timestamp, current_timestamp, null, 1024, 'png', 'lecture_thumbnail2', '/files/lecture_thumbnail2.png'),
    (1009, current_timestamp, current_timestamp, null, 4096, 'pdf', 'lecture_material3', '/files/lecture_material3.pdf'),
    (1010, current_timestamp, current_timestamp, null, 2048, 'jpg', 'lecture_thumbnail3', '/files/lecture_thumbnail3.jpg');

---------------------------
-- 2. "user" 테이블 (3건)
---------------------------
insert into "user" (id, created_at, updated_at, deleted_at, email, name, interest, oauth_identity, password, profile_image, provider, role)
values
    (1001, current_timestamp, current_timestamp, null, 'alswns11346@kgu.ac.kr', '박민준', 'FRONTEND', null, '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', '/profile/alice.jpg', 'GOOGLE', 'ADMIN'),
    (1002, current_timestamp, current_timestamp, null, 'user2@example.com', 'Bob', 'BACKEND', null, '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', '/profile/bob.jpg', 'KAKAO', 'ATTENDEE'),
    (1003, current_timestamp, current_timestamp, null, 'user3@example.com', 'Charlie', 'AI', null, '$2a$10$NkQj6yk0Xh4QhKevjrOkouQBymXUgpKqmHQFnTUKRaVhDrRZf5OTG', '/profile/charlie.jpg', 'GOOGLE', 'ATTENDEE');

---------------------------
-- 3. speaker 테이블 (3건)
---------------------------
insert into speaker (id, created_at, updated_at, deleted_at, image_file_id, email, filmography, introduction, name, position)
values
    (1001, current_timestamp, current_timestamp, null, 1004, 'speaker1@example.com', 'Filmography sample 1', 'Experienced speaker', 'Dr. Smith', 'Professor'),
    (1002, current_timestamp, current_timestamp, null, 1005, 'speaker2@example.com', 'Filmography sample 2', 'Tech expert', 'Dr. Jones', 'Senior Lecturer'),
    (1003, current_timestamp, current_timestamp, null, 1006, 'speaker3@example.com', 'Filmography sample 3', 'Industry leader', 'Dr. Brown', 'Chief Scientist');

---------------------------
-- 4. lecture 테이블 (3건)
---------------------------
insert into lecture (id, created_at, updated_at, deleted_at, start_time, end_time, material_file_id, thumbnail_file_id, speaker_id, max_capacity, category, contents, location, title)
values
    (1001, current_timestamp, current_timestamp, null, '2025-04-01 10:00:00', '2025-04-01 12:00:00', 1002, 1003, 1001, 100, 'AI', 'Lecture on AI', 'Auditorium', 'Introduction to AI'),
    (1002, current_timestamp, current_timestamp, null, '2025-04-02 14:00:00', '2025-04-02 16:00:00', 1007, 1008, 1002, 80, 'BACKEND', 'Lecture on Backend Fundamentals', 'Room 101', 'Backend Fundamentals'),
    (1003, current_timestamp, current_timestamp, null, '2025-04-03 09:00:00', '2025-04-03 11:00:00', 1009, 1010, 1003, 60, 'FRONTEND', 'Lecture on Frontend Basics', 'Room 102', 'Frontend Basics');

---------------------------
-- 5. log_event 테이블 (4건)
---------------------------
insert into log_event (lecture_id, timestamp, user_id, category, log_event_type, record)
values
    (1001, current_timestamp, 1001, 'AI', 'LECTURE_VIEW', 'LOG1001'),
    (1002, current_timestamp, 1002, 'BACKEND', 'LECTURE_RESERVATION_SUCCESS', 'LOG1002'),
    (1003, current_timestamp, 1002, 'FRONTEND', 'LECTURE_WISHLIST', 'LOG1003'),
    (1001, current_timestamp, 1002, 'AI', 'LECTURE_RESERVATION_FAIL', 'LOG1004');

---------------------------
-- 6. notification 테이블 (3건)
---------------------------
insert into notification (id, created_at, updated_at, deleted_at, user_id, contents, title)
values
    (1001, current_timestamp, current_timestamp, null, 1001, 'Your lecture is starting soon', 'Lecture Reminder'),
    (1002, current_timestamp, current_timestamp, null, 1002, 'New lecture available', 'New Lecture'),
    (1003, current_timestamp, current_timestamp, null, 1003, 'Don''t miss your lecture', 'Reminder');

---------------------------
-- 7. questions 테이블 (4건)
---------------------------
insert into questions (id, created_at, updated_at, deleted_at, lecture_id, user_id, content)
values
    (1001, current_timestamp, current_timestamp, null, 1001, 1001, 'What is AI?'),
    (1002, current_timestamp, current_timestamp, null, 1002, 1002, 'How to setup backend?'),
    (1003, current_timestamp, current_timestamp, null, 1003, 1003, 'What is CSS?'),
    (1004, current_timestamp, current_timestamp, null, 1001, 1003, 'Can you explain deep learning?');

---------------------------
-- 8. reservation 테이블 (6건)
---------------------------
insert into reservation (id, created_at, updated_at, deleted_at, lecture_id, user_id)
values
    (1001, current_timestamp, current_timestamp, null, 1001, 1001),
    (1002, current_timestamp, current_timestamp, null, 1002, 1002),
    (1003, current_timestamp, current_timestamp, null, 1003, 1003),
    (1004, current_timestamp, current_timestamp, null, 1001, 1002),
    (1005, current_timestamp, current_timestamp, null, 1001, 1003),
    (1006, current_timestamp, current_timestamp, null, 1002, 1001);

---------------------------
-- 9. wish_list 테이블 (5건)
---------------------------
insert into wish_list (id, created_at, updated_at, deleted_at, lecture_id, user_id)
values
    (1001, current_timestamp, current_timestamp, null, 1001, 1002),
    (1002, current_timestamp, current_timestamp, null, 1002, 1001),
    (1003, current_timestamp, current_timestamp, null, 1003, 1001),
    (1004, current_timestamp, current_timestamp, null, 1001, 1003),
    (1005, current_timestamp, current_timestamp, null, 1002, 1003);