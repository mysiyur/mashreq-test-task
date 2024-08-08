package org.example.repository;

import org.example.dto.RoomEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomsRepository extends ListCrudRepository<RoomEntity, UUID> {

    @Query("SELECT * FROM rooms WHERE capacity >= :capacity FOR UPDATE")
    List<RoomEntity> findSuitableRooms(int capacity);
}
