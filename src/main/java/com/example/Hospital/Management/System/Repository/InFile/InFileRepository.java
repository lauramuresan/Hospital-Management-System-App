package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class InFileRepository<T> implements AbstractRepository<T> {

    protected final ObjectMapper mapper;
    protected final String filePath;
    protected final Map<String, T> dataStore = new ConcurrentHashMap<>();
    protected final Class<T> entityType;

    protected abstract String getId(T entity);
    protected abstract void setId(T entity, String id);

    protected InFileRepository(ObjectMapper mapper, String dataFolder, String fileName) {
        mapper.registerModule(new JavaTimeModule());
        this.mapper = mapper;

        if (!dataFolder.endsWith("/")) {
            dataFolder = dataFolder + "/";
        }
        this.filePath = dataFolder + fileName;

        this.entityType = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PostConstruct
    public void loadData() {
        dataStore.clear();

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                mapper.writeValue(file, new ArrayList<T>());
                return;
            }

            try (InputStream inputStream = new FileInputStream(file)) {
                List<T> entities = mapper.readValue(
                        inputStream,
                        mapper.getTypeFactory()
                                .constructCollectionType(List.class, entityType)
                );

                entities.forEach(e -> dataStore.put(getId(e), e));
            }

        } catch (Exception e) {
            throw new RuntimeException("Eroare la încărcarea datelor din " + filePath, e);
        }
    }

    private synchronized void writeDataToFile() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            List<T> entities = new ArrayList<>(dataStore.values());
            mapper.writeValue(file, entities);

        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea datelor în " + filePath, e);
        }
    }

    @Override
    public synchronized void save(T entity) {
        String id = getId(entity);
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
            setId(entity, id);
        }
        dataStore.put(id, entity);
        writeDataToFile();
    }

    @Override
    public synchronized void delete(T entity) {
        String id = getId(entity);
        if (id != null) {
            dataStore.remove(id);
            writeDataToFile();
        }
    }

    @Override
    public synchronized T findById(String id) {
        return dataStore.get(id);
    }

    @Override
    public synchronized List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public synchronized List<T> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public synchronized List<T> findAll(Object searchCriteria, Sort sort) {
        List<T> list = findAll();

        if (searchCriteria != null) {
            list = list.stream().filter(entity -> matchesCriteria(entity, searchCriteria)).collect(Collectors.toList());
        }

        if (sort != null && sort.isSorted() && !list.isEmpty()) {
            ReflectionSorter.sortList(list, entityType, sort);
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
                        Field entityField = entityType.getDeclaredField(criteriaName);
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
}