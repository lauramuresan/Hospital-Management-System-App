package com.example.Hospital.Management.System.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class RepositoryModeHolder {
    private final AtomicReference<RepositoryMode> mode;//ofera referinta thread-safe, asigura synchronized

    public RepositoryModeHolder(@Value("${app.repository.mode:INMEMORY}") String initial) {
        this.mode = new AtomicReference<>(RepositoryMode.valueOf(initial.toUpperCase()));
    }

    public RepositoryMode getMode() {
        return mode.get();
    }

    public void setMode(RepositoryMode newMode) {
        mode.set(newMode);
    }
}
