-- formatted sql

-- changeset yury.mysin:2
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM rooms
INSERT INTO rooms (capacity, name) VALUES(5, 'Amaze');
INSERT INTO rooms (capacity, name) VALUES(7, 'Beauty');
INSERT INTO rooms (capacity, name) VALUES(12, 'Inspire');
INSERT INTO rooms (capacity, name) VALUES(20, 'Strive');