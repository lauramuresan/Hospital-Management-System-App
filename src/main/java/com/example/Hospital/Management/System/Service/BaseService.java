package com.example.Hospital.Management.System.Service;

import org.springframework.data.domain.Sort;
import java.util.List;

public abstract class BaseService<T> {

    public abstract T findById(String id);
    public abstract void save(T entity);
    protected abstract void delete(T entity);
    protected abstract List<T> findAll();

    protected abstract List<T> findAll(Sort sort);

    public void create(T entity) {
        save(entity);
    }
    public void remove(String id) {
        T entity = findById(id);
        if (entity != null) delete(entity);
    }
    public T getById(String id) {
        return findById(id);
    }
    public List<T> getAll() {
        return findAll();
    }

    public List<T> getAll(Sort sort) {
        return findAll(sort);
    }
}