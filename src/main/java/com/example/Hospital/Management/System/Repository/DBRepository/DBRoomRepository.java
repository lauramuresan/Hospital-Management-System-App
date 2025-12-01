package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBRoomRepository extends JpaRepository<RoomEntity, Long> {

    /**
     * Verifică dacă există o altă înregistrare RoomEntity cu același număr de cameră.
     * Această metodă este utilizată de RoomAdaptor pentru a implementa validarea unicizării
     * înainte de salvarea în baza de date.
     * * @param number Numărul camerei de verificat.
     * @return true dacă o cameră cu acest număr există deja, false altfel.
     */
    boolean existsByNumber(String number);
}