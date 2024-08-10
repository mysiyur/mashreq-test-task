-- formatted sql

-- changeset yury.mysin:1
CREATE TABLE IF NOT EXISTS active_bookings(
    id UUID DEFAULT random_uuid() PRIMARY KEY ,
    room_id UUID NOT NULL,
    booked_from TIMESTAMP NOT NULL,
    booked_until TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS rooms(
    id UUID DEFAULT random_uuid() PRIMARY KEY ,
    capacity DECIMAL(6, 0) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);