package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode; // IMPORT NOU
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder; // IMPORT NOU
import com.example.Hospital.Management.System.Utils.ReflectionSorter; // IMPORT NOU

import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RoomService extends BaseService<Room> {

    private final AbstractRepository<Room> roomRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public RoomService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
        this.roomRepository = factory.createRepository(Room.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Room entity){
        roomRepository.save(entity);
    }
    @Override
    protected void delete(Room entity){
        roomRepository.delete(entity);
    }
    @Override
    public Room findById(String id){
        return roomRepository.findById(id);
    }
    @Override
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    @Override
    protected List<Room> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return roomRepository.findAll(sort);
        } else {
            List<Room> rooms = roomRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(rooms, Room.class, sort);
            }
            return rooms;
        }
    }
}