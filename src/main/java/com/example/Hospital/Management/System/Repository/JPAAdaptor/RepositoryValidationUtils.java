package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Reusable repository validation helpers.
 */
public final class RepositoryValidationUtils {

    private RepositoryValidationUtils() { /* utility */ }

    public static void requireDomainNonNull(Object domain, String entityName) {
        if (domain == null) {
            throw new IllegalArgumentException(entityName + " must not be null");
        }
    }

    public static Long parseIdOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseIdOrThrow(String id, String errorMessage) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    public static void requireIdForDelete(String id, String entityName) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(entityName + " id is required for delete");
        }
        try {
            Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(entityName + " id is invalid", e);
        }
    }

    public static void assertNotExisting(Supplier<Boolean> existsCheck, String message) {
        Objects.requireNonNull(existsCheck, "existsCheck must not be null");
        if (Boolean.TRUE.equals(existsCheck.get())) {
            throw new IllegalStateException(message);
        }
    }
}
