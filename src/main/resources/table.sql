DROP TABLE roles, users, user_roles;

-- Table: users
CREATE TABLE users (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
userName VARCHAR(255) NOT NULL,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL
);

-- Table: roles
CREATE TABLE roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);


-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
user_id INT NOT NULL,
role_id INT NOT NULL,

FOREIGN KEY (user_id) REFERENCES users (id),
FOREIGN KEY (role_id) REFERENCES roles (id),

UNIQUE (user_id, role_id)
);



INSERT INTO users
VALUES (1, 'admin', 'q', 'q', 'q@q', '$2a$12$glCq62X8t.mNMwGzAgohIuLGed9TmfE2XVbfuQMTtQld7V9gHYVFu');
INSERT INTO users
VALUES (2, 'user', 'e', 'e', 'e@e', '$2a$12$glCq62X8t.mNMwGzAgohIuLGed9TmfE2XVbfuQMTtQld7V9gHYVFu');
INSERT INTO users
VALUES (3, 'user1', 'w', 'w', 'w@w', '$2a$12$glCq62X8t.mNMwGzAgohIuLGed9TmfE2XVbfuQMTtQld7V9gHYVFu');

INSERT INTO roles
VALUES (1, 'ROLE_USER');
INSERT INTO roles
VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles
VALUES (1, 2);
INSERT INTO user_roles
VALUES (2, 1);
INSERT INTO user_roles
VALUES (3, 1);

SELECT *
FROM users;
SELECT *
FROM roles;
SELECT *
FROM user_roles;