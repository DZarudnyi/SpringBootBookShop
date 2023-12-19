package com.example.books.repository;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    default Specification<T> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(this.getKey()).in(Arrays.stream(params).toArray());
    }
}
