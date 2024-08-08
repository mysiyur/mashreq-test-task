-- All of those need to be migrated into Flyway/Liquibase to perform database migrations upon release
CREATE TABLE active_bookings(
    id UUID PRIMARY KEY,
    room_id UUID NOT NULL,
    booked_from TIMESTAMP NOT NULL,
    booked_until TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE rooms(
    id UUID PRIMARY KEY,
    capacity DECIMAL(6, 0) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)