package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Room;
import org.springframework.stereotype.Repository;
@Repository
public class RoomRepository extends InMemoryRepository<Room> {
    @Override
    protected String getId(Room room) {
        return room.getRoomID();
    }
    @Override
    protected void setId(Room room, String id) {
        room.setRoomID(id);
    }
}
