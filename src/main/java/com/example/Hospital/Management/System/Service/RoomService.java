package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.MedicalStaff;
import com.example.Hospital.Management.System.Model.Room;
import com.example.Hospital.Management.System.Repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService extends BaseService<Room> {

    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    protected void save(Room entity){
        roomRepository.save(entity);
    }
    protected void delete(Room entity){
        roomRepository.delete(entity);
    }
    protected Room findById(String id){
        return roomRepository.findById(id);
    }
    protected List<Room> findAll(){
        return roomRepository.findAll();
    }
}
