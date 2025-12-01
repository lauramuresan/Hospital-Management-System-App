package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adăugat
import java.util.List;

@Service
@Transactional // Adăugat
public class RoomService extends BaseService<Room> {

    private final AbstractRepository<Room> roomRepository;

    public RoomService(RepositoryFactory factory) {
        this.roomRepository = factory.createRepository(Room.class);
    }

    @Override
    public void save(Room entity){
        roomRepository.save(entity);
    }
    @Override
    protected void delete(Room entity){ // Adăugat @Override
        roomRepository.delete(entity);
    }
    @Override
    public Room findById(String id){ // Vizibilitate uniformizată și @Override
        return roomRepository.findById(id);
    }
    @Override
    public List<Room> findAll(){ // Vizibilitate uniformizată și @Override
        return roomRepository.findAll();
    }
}