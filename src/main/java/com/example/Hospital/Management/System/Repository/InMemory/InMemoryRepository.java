package com.example.Hospital.Management.System.Repository.InMemory;

import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
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
        return findAll(null, sort);
    }

    @Override
    public List<T> findAll(Object searchCriteria, Sort sort) {
        List<T> list = findAll();

        if (searchCriteria != null) {
            list = list.stream().filter(entity -> matchesCriteria(entity, searchCriteria)).collect(Collectors.toList());
        }

        if (sort != null && sort.isSorted() && !list.isEmpty()) {
            @SuppressWarnings("unchecked")
            Class<T> domainClass = (Class<T>) list.get(0).getClass();
            ReflectionSorter.sortList(list, domainClass, sort);
        }

        return list;
    }

    private boolean matchesCriteria(T entity, Object criteria) {
        Class<?> criteriaClass = criteria.getClass();

        for (Field criteriaField : criteriaClass.getDeclaredFields()) {
            criteriaField.setAccessible(true);
            try {
                Object criteriaValue = criteriaField.get(criteria);
                String criteriaName = criteriaField.getName();

                if (criteriaValue != null && criteriaValue instanceof String && !((String) criteriaValue).isEmpty()) {

                    try {
                        Field entityField = entity.getClass().getDeclaredField(criteriaName);
                        entityField.setAccessible(true);
                        Object entityValue = entityField.get(entity);

                        if (entityValue != null) {
                            String entityString = entityValue.toString().toLowerCase();
                            String criteriaString = ((String) criteriaValue).toLowerCase();

                            if (!entityString.contains(criteriaString)) {
                                return false;
                            }
                        }
                    } catch (NoSuchFieldException ignored) {
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
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