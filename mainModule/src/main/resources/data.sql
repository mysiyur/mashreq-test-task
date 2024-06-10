INSERT INTO request_category (id, name) VALUES ('986e3cd6-6948-4e38-9509-5db110734c77', 'WORK_REMOTELY');
INSERT INTO request_category (id, name) VALUES ('c925fb58-fa3e-4cba-8de1-1f4353ebeda7', 'ANNUAL_LEAVE');
INSERT INTO request_category (id, name) VALUES ('d2ce9084-046c-4039-8a54-272280ae9123', 'SICK_LEAVE');

INSERT INTO employee (id, email, name, position, country, salary)
VALUES ('1c9b19c7-8ac4-4520-8abe-bd0de713b8e8', 'testUser@cercli.org', 'Test User', 'Testing stuff', 'RU', 1000);
INSERT INTO employee (id, email, name, position, country, salary)
VALUES ('3615f834-27fc-4ebf-b22c-91c9a40cf8ad', 'testUser2@cercli.org', 'Test User 2', 'Testing stuff', 'GB', 1000);
INSERT INTO time_off_request (id, employee_id, request_category_id, start_date, end_date) VALUES
    ('cfa3fd9d-c004-41c0-8d8b-8e846768e612', '1c9b19c7-8ac4-4520-8abe-bd0de713b8e8', 'c925fb58-fa3e-4cba-8de1-1f4353ebeda7', '2024-06-10', '2024-06-13');