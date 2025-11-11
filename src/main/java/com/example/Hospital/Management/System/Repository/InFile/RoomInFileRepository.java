package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("roomInFile")
public class RoomInFileRepository extends InFileRepository<Room> {
    public RoomInFileRepository(ObjectMapper mapper, @Value("${app.data.folder:data/}") String dataFolder) {
        super(mapper, dataFolder, "rooms.json");
    }

    @Override
    protected String getId(Room room) {
        return room.getRoomID();
    }

    @Override
    protected void setId(Room room, String id) {
        room.setRoomID(id);
    }
}