package com.example.Hospital.Management.System.Mapper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    public static Long parseLong(String value) {
        if (value == null) return null;
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID: " + value, e);
        }
    }

    public static <T> T createEntityProxy(Class<T> clazz, String id) {
        if (id == null) return null;
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setId", Long.class).invoke(entity, parseLong(id));
            return entity;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID for " + clazz.getSimpleName() + ": " + id, e);
        }
    }

    public static <D, E> List<E> mapListWithParent(List<D> domains, Function<D, E> mapper, Consumer<E> setParent) {
        if (domains == null) return null;
        return domains.stream().map(d -> {
            E e = mapper.apply(d);
            setParent.accept(e);
            return e;
        }).collect(Collectors.toList());
    }
}
