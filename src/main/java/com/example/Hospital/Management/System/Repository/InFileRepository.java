package com.example.Hospital.Management.System.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class InFileRepository<T> implements AbstractRepository<T>{
    private final Path filePath;
    private final ObjectMapper mapper;

    protected abstract String getId(T entity);
    protected abstract void setId(T entity, String id);

    protected InFileRepository(ObjectMapper mapper, String dataFolder, String fileName) {
        this.mapper = mapper;
        this.filePath = Paths.get(dataFolder).resolve(fileName);
        ensureFileExists();
    }
    private synchronized List<T> readAllFromFile(){
        try{
            if(!Files.exists(filePath) || Files.size(filePath) == 0){
                return new ArrayList<>();
            }
            byte[] bytes = Files.readAllBytes(filePath);
            TypeReference<List<T>> typeRef = new TypeReference<>(){};
            return mapper.readValue(bytes, typeRef);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read file " + filePath, e);
        }
    }
    private synchronized void writeAllToFile(List<T> list) {
        try {
            Files.createDirectories(filePath.getParent());
            byte[] bytes = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(list);
            Files.write(filePath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data file: " + filePath, e);
        }
    }
    private void ensureFileExists() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                writeAllToFile(new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create data directory/file: " + filePath, e);
        }
    }
    @Override
    public synchronized void save(T entity) {
        List<T> all = readAllFromFile();
        String id = getId(entity);
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
            setId(entity, id);
        }
        final String finalId = id;
        List<T> filtered = new ArrayList<>(
                all.stream()
                        .filter(e -> {
                            String entityId = getId(e);
                            return entityId == null || !entityId.equals(finalId);
                        })
                        .toList()
        );
        filtered.add(entity);
        writeAllToFile(filtered);
    }

    @Override
    public synchronized void delete(T entity){
        String id = getId(entity);
        if(id == null) return;
        List<T> all = readAllFromFile();
        List<T> remaining = new ArrayList<>(
                all.stream()
                        .filter(e -> {
                            String entityId = getId(e);
                            return entityId == null && !entityId.equals(id);
                        })
                        .toList());
                writeAllToFile(remaining);

    }

    @Override
    public synchronized T findById(String id){
        if(id == null) return null;
        List<T> all = readAllFromFile();
        return all.stream()
                .filter(e -> {
                    String entityId = getId(e);
                    return entityId == null && !entityId.equals(id);
                })
                .findFirst()
                .orElse(null);
    }
    @Override
    public synchronized List<T> findAll() {
        return readAllFromFile();
    }

    public synchronized void replaceAll(List<T> list) {
        writeAllToFile(list);
    }
}

