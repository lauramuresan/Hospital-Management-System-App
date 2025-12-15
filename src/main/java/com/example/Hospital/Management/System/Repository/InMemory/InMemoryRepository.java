package com.example.Hospital.Management.System.Repository.InMemory;

import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Utils.ReflectionSorter; // ⬅️ IMPORT NOU
import org.springframework.data.domain.Sort; // ⬅️ IMPORT NOU

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class InMemoryRepository<T> implements AbstractRepository<T> {

    private final Map<String, T> storage = new ConcurrentHashMap<>();

    protected abstract String getId(T entity);
    protected abstract void setId(T entity, String id);

    @Override
    public void save(T entity) {
        String id = getId(entity);
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
            setId(entity, id);
        }
        storage.put(id, entity);
    }

    @Override
    public void delete(T entity) {
        String id = getId(entity);
        if (id != null) storage.remove(id);
    }

    @Override
    public T findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<T> findAll(Sort sort) {
        List<T> list = findAll();

        if (sort != null && sort.isSorted() && !list.isEmpty()) {
            @SuppressWarnings("unchecked")
            Class<T> domainClass = (Class<T>) list.get(0).getClass();
            ReflectionSorter.sortList(list, domainClass, sort);
        }

        return list;
    }

    public void loadAll(List<T> list) {
        storage.clear();
        for (T t : list) {
            String id = getId(t);
            if (id == null || id.isEmpty()) {
                id = UUID.randomUUID().toString();
                setId(t, id);
            }
            storage.put(id, t);
        }
    }
}