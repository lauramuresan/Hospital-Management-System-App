package com.example.Hospital.Management.System.Mapper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    public static Long parseLong(String value) {
        if (value == null) return null;

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) return null;

        try {
            return Long.valueOf(trimmedValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static <T> T createEntityProxy(Class<T> clazz, String id) {
        Long parsedId = parseLong(id);
        if (parsedId == null) return null;

        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setId", Long.class).invoke(entity, parsedId);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Eroare la crearea proxy-ului pentru " + clazz.getSimpleName(), e);
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