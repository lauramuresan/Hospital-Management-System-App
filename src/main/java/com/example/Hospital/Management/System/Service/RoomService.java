package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.SearchCriteria.RoomSearchCriteria;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService extends BaseService<Room> {

    private final AbstractRepository<Room> roomRepository;
    private final RepositoryModeHolder modeHolder;

    public RoomService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
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
        return findAllFiltered("roomID", "asc", null);
    }

    public List<Room> findAllFiltered(String sortField, String sortDir, RoomSearchCriteria criteria) {
        // Mapare câmpuri sortare
        String dbSortField = sortField;
        if ("roomID".equals(sortField)) dbSortField = "id";
        if ("hospitalID".equals(sortField) || "hospital.id".equals(sortField)) dbSortField = "hospital.id";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return roomRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback
            List<Room> rooms = roomRepository.findAll();

            if (sort != null && sort.isSorted()) {
                if (!dbSortField.contains(".")) {
                    ReflectionSorter.sortList(rooms, Room.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getNumber() != null && !criteria.getNumber().isEmpty()) {
                    rooms = rooms.stream().filter(r -> r.getNumber().toLowerCase().contains(criteria.getNumber().toLowerCase())).collect(Collectors.toList());
                }
                if (criteria.getHospitalID() != null && !criteria.getHospitalID().isEmpty()) {
                    rooms = rooms.stream().filter(r -> r.getHospitalID().equals(criteria.getHospitalID())).collect(Collectors.toList());
                }
                if (criteria.getStatus() != null) {
                    rooms = rooms.stream().filter(r -> r.getStatus() == criteria.getStatus()).collect(Collectors.toList());
                }
                if (criteria.getMinCapacity() != null) {
                    rooms = rooms.stream().filter(r -> r.getCapacity() >= criteria.getMinCapacity()).collect(Collectors.toList());
                }
                if (criteria.getMaxCapacity() != null) {
                    rooms = rooms.stream().filter(r -> r.getCapacity() <= criteria.getMaxCapacity()).collect(Collectors.toList());
                }
            }
            return rooms;
        }
    }
}