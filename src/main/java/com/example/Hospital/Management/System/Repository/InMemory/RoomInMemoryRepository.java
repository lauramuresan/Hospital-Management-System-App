package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import org.springframework.stereotype.Repository;
@Repository("roomInMemory")
public class RoomInMemoryRepository extends InMemoryRepository<Room> {
    @Override
    protected String getId(Room room) {
        return room.getRoomID();
    }
    @Override
    protected void setId(Room room, String id) {
        room.setRoomID(id);
    }
}
