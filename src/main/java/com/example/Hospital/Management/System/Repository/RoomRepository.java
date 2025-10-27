package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RoomRepository implements AbstractRepository<Room> {
    private final HashMap<String, Room> rooms = new HashMap<>();

    @Override
    public void save(Room room) {
        if(room==null || room.getRoomID().isEmpty()){
            room.setRoomID(UUID.randomUUID().toString());
        }
        rooms.put(room.getRoomID(), room);
    }
    @Override
    public void delete(Room room) {
        rooms.remove(room.getRoomID());
    }
    @Override
    public Room findById(String id) {
        return rooms.get(id);
    }
    @Override
    public List<Room> findAll(){
        return new ArrayList<>(rooms.values());
    }
}
