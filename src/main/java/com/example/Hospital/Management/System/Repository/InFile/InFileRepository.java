package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

            // Creează folderul și fișierul dacă nu există
            if (!file.exists()) {
                System.out.println("Fișierul nu există, îl creez: " + filePath);
                file.getParentFile().mkdirs();
                mapper.writeValue(file, new ArrayList<T>());
                return;
            }

            // Dacă fișierul există, îl citim
            try (InputStream inputStream = new FileInputStream(file)) {
                List<T> entities = mapper.readValue(
                        inputStream,
                        mapper.getTypeFactory()
                                .constructCollectionType(List.class, entityType)
                );

                entities.forEach(e -> dataStore.put(getId(e), e));

                System.out.println(dataStore.size() + " "
                        + entityType.getSimpleName()
                        + " încărcate din " + filePath);
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

            System.out.println("Date salvate în " + filePath);
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
}
