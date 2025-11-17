package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.io.IOException;
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
        // Înregistrează modulul Java Time pentru a citi LocalDateTime/LocalDate
        mapper.registerModule(new JavaTimeModule());

        this.mapper = mapper;

        String cleanedFolder = dataFolder.replace("./", "");
        if (!cleanedFolder.endsWith("/")) {
            cleanedFolder = cleanedFolder + "/";
        }
        this.filePath = cleanedFolder + fileName;
        // ----------------------------------------------------

        // Determinăm tipul generic T la runtime
        this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PostConstruct
    public void loadData() {
        dataStore.clear();

        try {
            // Aici filePath va fi acum corect, de exemplu: "data/appointments.json"
            Resource resource = new ClassPathResource(filePath);

            if (!resource.exists() || resource.contentLength() == 0) {
                System.out.println("Fișierul de date nu există sau este gol (în classpath): " + filePath);
                return;
            }
            // ... (restul metodei loadData() rămâne la fel)

            try (InputStream inputStream = resource.getInputStream()) {
                // Citirea datelor ca Listă de obiecte de tipul T
                List<T> entities = mapper.readValue(inputStream, new TypeReference<List<T>>() {
                    @Override
                    public java.lang.reflect.Type getType() {
                        return mapper.getTypeFactory().constructCollectionType(List.class, entityType);
                    }
                });

                // Populează dataStore
                entities.forEach(e -> dataStore.put(getId(e), e));

                System.out.println("✅ " + dataStore.size() + " " + entityType.getSimpleName() + " încărcate din " + filePath);
            }

        } catch (IOException e) {
            System.err.println("❌ EROARE CRITICĂ la încărcarea datelor din fișier: " + filePath);
            e.printStackTrace();
            throw new RuntimeException("Eșec la inițializarea InFileRepository.", e);
        }
    }

    // (Metodele save, delete, findById, findAll rămân la fel)

    @Override
    public synchronized void save(T entity) {
        String id = getId(entity);
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
            setId(entity, id);
        }
        dataStore.put(id, entity);
    }

    @Override
    public synchronized void delete(T entity) {
        String id = getId(entity);
        if (id != null) dataStore.remove(id);
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