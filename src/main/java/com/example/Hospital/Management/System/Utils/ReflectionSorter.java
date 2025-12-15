package com.example.Hospital.Management.System.Utils;

import org.springframework.data.domain.Sort;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ReflectionSorter {

    private ReflectionSorter() {

    }

    public static <T> void sortList(List<T> list, Class<T> entityClass, Sort sort) {
        if (sort == null || !sort.isSorted() || list == null || list.isEmpty()) {
            return;
        }

        Comparator<T> comparator = null;

        for (Sort.Order order : sort) {
            final String property = order.getProperty();
            final Sort.Direction direction = order.getDirection();

            try {
                String getterMethodName = "get" +
                        property.substring(0, 1).toUpperCase() + property.substring(1);

                final Method getter = entityClass.getMethod(getterMethodName);

                Comparator<T> currentComparator = (h1, h2) -> {
                    try {
                        Comparable<Object> val1 = (Comparable<Object>) getter.invoke(h1);
                        Comparable<Object> val2 = (Comparable<Object>) getter.invoke(h2);

                        if (val1 == null && val2 == null) return 0;
                        if (val1 == null) return direction == Sort.Direction.ASC ? -1 : 1;
                        if (val2 == null) return direction == Sort.Direction.ASC ? 1 : -1;

                        return val1.compareTo(val2);
                    } catch (Exception e) {
                        throw new RuntimeException("Eroare la sortare prin reflexie pe proprietatea: " + property, e);
                    }
                };

                if (direction == Sort.Direction.DESC) {
                    currentComparator = currentComparator.reversed();
                }

                comparator = (comparator == null) ? currentComparator : comparator.thenComparing(currentComparator);

            } catch (NoSuchMethodException ignored) {
            }
        }

        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }
}