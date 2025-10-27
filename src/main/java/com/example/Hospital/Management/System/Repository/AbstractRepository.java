package com.example.Hospital.Management.System.Repository;
import java.util.List;
public interface AbstractRepository<T> {
    void save(T t);
    void delete(T t);
    T findById(String id);
    List<T> findAll();
}
