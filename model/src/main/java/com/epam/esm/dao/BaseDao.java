package com.epam.esm.dao;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    T insert(T t);

    Optional<T> getById(long id);

    List<T> getAll(Pageable pageable);

    void removeById(long id);

}
