package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.GeneralModel.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Sort; // IMPORT NECESAR

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class RepositoryFactory {

    private final ApplicationContext context;
    private final RepositoryModeHolder modeHolder;

    public RepositoryFactory(ApplicationContext context, RepositoryModeHolder modeHolder) {
        this.context = context;
        this.modeHolder = modeHolder;
    }

    public <T> AbstractRepository<T> createRepository(Class<T> entityClass) {
        RepositoryMode mode = modeHolder.getMode();
        String beanName = getBeanName(entityClass, mode);

        if (context.containsBean(beanName)) {
            //noinspection unchecked
            return (AbstractRepository<T>) context.getBean(beanName);
        }

        // --- AICI ERA EROAREA ---
        // Aceasta este clasa anonimă (fallback) care trebuie să implementeze TOATE metodele din interfață
        return new AbstractRepository<T>() {
            @Override
            public void save(T t) {
                System.out.println("Fallback repository: save called (No implementation found)");
            }

            @Override
            public void delete(T t) {
                System.out.println("Fallback repository: delete called (No implementation found)");
            }

            @Override
            public T findById(String id) {
                return null;
            }

            @Override
            public List<T> findAll() {
                return Collections.emptyList();
            }

            @Override
            public List<T> findAll(Sort sort) {
                return Collections.emptyList();
            }

            @Override
            public List<T> findAll(Object searchCriteria, Sort sort) {
                return Collections.emptyList();
            }
        };
    }

    private String getBeanName(Class<?> entityClass, RepositoryMode mode) {
        String baseName = entityClass.getSimpleName().toLowerCase(Locale.ROOT);



        if (mode == RepositoryMode.INMEMORY) {
            return baseName + "InMemory"; // ex: patientInMemory
        } else if (mode == RepositoryMode.INFILE) {
            return baseName + "InFile";   // ex: patientInFile
        } else {

            return baseName + "Adaptor";
        }
    }
}