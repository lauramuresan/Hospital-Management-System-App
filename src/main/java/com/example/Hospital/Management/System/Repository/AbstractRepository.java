package com.example.Hospital.Management.System.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;
public interface AbstractRepository<T> {
    void save(T t);
    void delete(T t);
    T findById(String id);
    List<T> findAll();
    List<T> findAll(Sort sort);
    List<T> findAll(Object searchCriteria, Sort sort);
}
