package com.example.cryptocurrencyapp.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortCryptoUtil {
    private static final String PARAMETER_SPLITERATOR = ";";
    private static final String ORDER_SPLITERATOR = ":";

    public static Sort getSortingCryptos(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(ORDER_SPLITERATOR)) {
            String[] sortingFields = sortBy.split(PARAMETER_SPLITERATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(ORDER_SPLITERATOR)) {
                    String[] fieldAndDirections = field.split(ORDER_SPLITERATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[1]),
                            fieldAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
