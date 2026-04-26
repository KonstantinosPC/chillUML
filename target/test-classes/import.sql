DELETE FROM users;

ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (username, password, email)
VALUES ('kgeorgiou', 'kostas123', 'kgeorgiou@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('spappas', 'stefanos123', 'spappas@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('christos', 'christos123', 'christos@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('bob', 'bob123', 'bob@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('mary', 'mary123', 'mary@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('willie', 'willie123', 'willie@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('martin', '805bd951772627f3d1a607084df1727c6caad60447c5d73febf7be2d2fe17fd8', 'martin@gmail.com');

INSERT INTO users (username, password, email)
VALUES ('kent', 'kent123', 'kent@gmail.com');

INSERT INTO projects (id, name, project_description, owner) VALUES (1, 'Chill UML Editor', 'A web based UML creator', 3);

INSERT INTO projects (id, name, project_description, owner) VALUES (2, 'Database Schema', 'Backend schema design', 3);

INSERT INTO projects (id, name, project_description, owner) VALUES (3, 'Secret Project', 'Top secret documentation', 1);