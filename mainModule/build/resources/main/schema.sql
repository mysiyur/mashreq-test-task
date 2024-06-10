-- All of those need to be migrated into Flyway/Liquibase to perform database migrations upon release
 CREATE TABLE IF NOT EXISTS public.employee(
                    id UUID  DEFAULT random_uuid() NOT NULL PRIMARY KEY,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    name VARCHAR(100) NOT NULL,
                    position VARCHAR(100) NOT NULL,
                    country VARCHAR(5) NOT NULL,
                    salary NUMBER(10, 6) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                );
CREATE TABLE IF NOT EXISTS public.request_category(
                    id UUID DEFAULT random_uuid() NOT NULL PRIMARY KEY,
                    name VARCHAR(100) UNIQUE NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                );
CREATE TABLE IF NOT EXISTS public.time_off_request(
                    id UUID DEFAULT random_uuid() NOT NULL PRIMARY KEY,
                    employee_id UUID NOT NULL,
                    request_category_id UUID NOT NULL,
                    start_date TIMESTAMP NOT NULL,
                    end_date TIMESTAMP NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                );

ALTER TABLE time_off_request ADD CONSTRAINT time_off_request_employee_id_fk
FOREIGN KEY (employee_id) REFERENCES employee (id);
ALTER TABLE time_off_request ADD CONSTRAINT time_off_request_request_category_id_fk
FOREIGN KEY (request_category_id) REFERENCES request_category (id);
ALTER TABLE time_off_request ADD CHECK start_date <= end_date;
ALTER TABLE employee ADD CHECK salary >= 0;