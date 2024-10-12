CREATE TABLE IF NOT EXISTS member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(60) NOT NULL,
    encoded_password VARCHAR(255) NOT NULL,
    nickname VARCHAR(16) NOT NULL,
    university VARCHAR(16) NOT NULL,
    student_id VARCHAR(16) NOT NULL,
    status VARCHAR(255) NOT NULL
);

INSERT INTO member (email, encoded_password, nickname, university, student_id, status) VALUES
('test@example.com', 'encodedPassword123', 'TestNickname', 'EWHA', '2103044', 'REGISTERED');

CREATE TABLE IF NOT EXISTS board (
    board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    board_name VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    notice VARCHAR(100) NOT NULL,
    host_nickname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

CREATE TABLE IF NOT EXISTS post (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    anonymous BOOLEAN NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES board(board_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

INSERT INTO board (member_id, board_name, description, notice, host_nickname) VALUES
(1, '테스트 게시판', '테스트용 게시판입니다.', '공지입니다.', 'TestNickname');

INSERT INTO post (board_id, member_id, anonymous, content)
VALUES (1, 1, TRUE, '테스트용 게시글입니다.');
