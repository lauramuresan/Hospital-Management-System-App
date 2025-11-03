package com.example.Hospital.Management.System.Service;

import java.util.List;

public abstract class BaseService<T> {

    protected abstract T findById(String id);
    protected abstract void save(T entity);
    protected abstract void delete(T entity);
    protected abstract List<T> findAll();

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

}
