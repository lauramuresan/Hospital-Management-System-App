package com.example.Hospital.Management.System.Mapper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    /**
     * CORECȚIA CRUCIALĂ:
     * 1. Folosește .trim() pentru a elimina spațiile.
     * 2. Returnează null în caz de String gol sau eșec de conversie (NumberFormatException),
     * permițând Adaptorului să trateze ID-ul ca inexistent.
     */
    public static Long parseLong(String value) {
        if (value == null) return null;

        String trimmedValue = value.trim();
        // Dacă valoarea este goală după trim() (ex: selectarea opțiunii goale din formular)
        if (trimmedValue.isEmpty()) return null;

        try {
            return Long.valueOf(trimmedValue);
        } catch (NumberFormatException e) {
            // Dacă nu este un număr valid (ex: "P1" sau text), returnăm null
            return null;
        }
    }

    /**
     * Creează un proxy de entitate folosind doar ID-ul, pentru setarea relațiilor ForeignKey (FK).
     * Acum folosește parseLong() corectată.
     */
    public static <T> T createEntityProxy(Class<T> clazz, String id) {
        Long parsedId = parseLong(id);
        // Dacă ID-ul nu poate fi parsat (parsedId este null), returnăm null pentru proxy.
        // Adaptorul va verifica apoi dacă acest lucru este permis.
        if (parsedId == null) return null;

        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            // Asumăm că entitatea are o metodă setId(Long id)
            clazz.getMethod("setId", Long.class).invoke(entity, parsedId);
            return entity;
        } catch (Exception e) {
            // Menținem excepția pentru erori de Reflexie (Constructor lipsă, metodă setId),
            // dar nu pentru erori de conversie ID.
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