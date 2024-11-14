-- Создание таблицы users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       roles VARCHAR(50) NOT NULL
);

-- Создание таблицы meetings
CREATE TABLE meetings (
                          id SERIAL PRIMARY KEY,
                          description TEXT NOT NULL,
                          start_time TIMESTAMP NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          comment TEXT,
                          end_time TIMESTAMP NOT NULL,
                          user_id BIGINT NOT NULL,
                          expert_id BIGINT NOT NULL,
                          CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                          CONSTRAINT fk_expert FOREIGN KEY (expert_id) REFERENCES users(id)
);

-- Создание таблицы meeting_slots
CREATE TABLE meeting_slots (
                               id SERIAL PRIMARY KEY,
                               start_time TIMESTAMP NOT NULL,
                               end_time TIMESTAMP NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               description TEXT NOT NULL,
                               user_id BIGINT NOT NULL,
                               CONSTRAINT fk_user_meeting_slot FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Вставка данных в таблицу users
INSERT INTO users (username, password, roles) VALUES
                                                                 ('joe', '$2y$10$ABooq0856y5bC7wlqpxlQeJBGPyoc3tKhBcUwDYUfHit6eyxl1EeW', 'ROLE_EXPERT'),
                                                                 ('donald', '$2y$10$kXh1WoAG2c6EHThjBuDSy.U.6qLVLg9VwatIeKyXNKrWqBDcZjYY2', 'ROLE_EXPERT'),
                                                                 ('kamala', '$2y$10$RhIZJ55.zH5H3otCdaun9e51ydIwBnVHMmGXCwUxRiOBEEL12Jd/2', 'ROLE_EXPERT'),
                                                                 ('robert', '$2y$10$hVlNaqQmv5CDBgbXS2RSW..VAi2o5KI2tGMJLuYG/RNmHZe8qd7u2', 'ROLE_EXPERT'),
                                                                 ('sergey', '$2y$10$Kk94bngzydh3AFJGMEzOzeMGvqdz3a5eRIgwSg4ZyAINgk0/E8Ul2', 'ROLE_USER'),
                                                                 ('john', '$2y$10$eo6ge8xFZcGHqJFQNAo6kelVGF9nJX9Vb6qFRGBAfkwDLvCDpxf8K', 'ROLE_USER');

-- Вставка данных в таблицу meetings
INSERT INTO meetings (description, start_time, name, comment, end_time, user_id, expert_id) VALUES
                                                                                                ('Project kickoff meeting', '2024-11-10 10:00:00', 'Kickoff', null, '2024-11-10 11:00:00', 5, 2),
                                                                                                ('Review system', '2024-11-13 10:00:00', 'Review', null, '2024-11-13 11:00:00', 5, 2),
                                                                                                ('Troubles with me', '2024-11-10 15:00:00', 'Aloha', null, '2024-11-10 16:00:00', 5, 4),
                                                                                                ('Data review session', '2024-11-15 16:00:00', 'Data Review', null, '2024-11-15 17:30:00', 6, 4),
                                                                                                ('Clean Room for my buddies', '2024-11-16 14:00:00', 'Party', null, '2024-11-16 15:30:00', 6, 1),
                                                                                                ('Something wrong with code', '2024-11-15 14:00:00', 'Nothing', null, '2024-11-15 15:30:00', 6, 1);

-- Вставка данных в таблицу meeting_slots
INSERT INTO meeting_slots (start_time, end_time, name, description, user_id) VALUES
                                                                                 ('2024-11-12 09:00:00', '2024-11-12 10:00:00', 'Consultation Slot 1', 'Available for consultation', 2),
                                                                                 ('2024-11-12 09:00:00', '2024-11-12 10:00:00', 'Consultation Slot 1', 'Available for consultation', 3),
                                                                                 ('2024-11-12 10:00:00', '2024-11-12 12:00:00', 'Consultation Slot 2', 'Discuss project requirements', 2),
                                                                                 ('2024-11-14 13:00:00', '2024-11-14 14:00:00', 'Consultation Slot 2', 'Discuss project requirements', 3);
