-- Enum 타입 생성
CREATE TYPE base_role AS ENUM ('ATTENDEE', 'SPEAKER', 'ADMIN');
CREATE TYPE category AS ENUM ('BACKEND', 'FRONTEND', 'AI', 'DATA_SCIENCE', 'CLOUD', 'DEVOPS', 'DESIGN', 'SECURITY', 'PRODUCT_MANAGEMENT');
CREATE TYPE provider AS ENUM ('KAKAO', 'GOOGLE');
CREATE TYPE lecture_status AS ENUM ('PENDING_APPROVAL', 'APPROVED', 'PENDING_DELETION', 'DELETED');

-- "user" 테이블 생성 (예약어 충돌 방지를 위해 이름을 큰따옴표로 감쌈)
CREATE TABLE "user" (
                        id BIGSERIAL PRIMARY KEY,
                        oauth_identity TEXT UNIQUE,
                        email TEXT NOT NULL UNIQUE,
                        password TEXT,
                        profile_image TEXT,
                        name TEXT NOT NULL,
                        role base_role,
                        provider provider,
                        interest category,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP
);

-- lecture 테이블 생성 (speaker_id는 "user" 테이블을 참조)
CREATE TABLE lecture (
                         id BIGSERIAL PRIMARY KEY,
                         speaker_id BIGINT,
                         title TEXT NOT NULL UNIQUE,
                         contents TEXT NOT NULL,
                         max_capacity BIGINT NOT NULL,
                         start_time TIMESTAMP NOT NULL,
                         end_time TIMESTAMP NOT NULL,
                         location TEXT NOT NULL,
                         category category NOT NULL,
                         lecture_status lecture_status NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         deleted_at TIMESTAMP,
                         CONSTRAINT fk_lecture_speaker FOREIGN KEY (speaker_id) REFERENCES "user"(id)
);

-- notification 테이블 생성 (user를 참조)
CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT,
                              title TEXT NOT NULL,
                              contents TEXT NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              deleted_at TIMESTAMP,
                              CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- reservation 테이블 생성 (user, lecture 참조)
CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             lecture_id BIGINT NOT NULL,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             deleted_at TIMESTAMP,
                             CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                             CONSTRAINT fk_reservation_lecture FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

-- review 테이블 생성 (user, lecture 참조)
CREATE TABLE review (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        lecture_id BIGINT NOT NULL,
                        content TEXT NOT NULL,
                        rating DOUBLE PRECISION NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                        CONSTRAINT fk_review_lecture FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

-- wish_list 테이블 생성 (user, lecture 참조)
CREATE TABLE wish_list (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           lecture_id BIGINT NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           deleted_at TIMESTAMP,
                           CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                           CONSTRAINT fk_wishlist_lecture FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);
