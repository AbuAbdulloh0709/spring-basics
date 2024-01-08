package com.epam.esm.dao;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    /**
     * Method for saving an entity.
     *
     * @param t entity to save
     * @return saved entity
     */
    T insert(T t);

    /**
     * Method for getting an entity object by ID.
     *
     * @param id ID of entity to get
     * @return optional Entity object
     */
    Optional<T> getById(long id);

    /**
     * Method for getting all entities.
     *
     * @param pageable pageable object for pagination
     * @return List of all entities
     */

    List<T> getAll(Pageable pageable);

    /**
     * Method for getting all entities.
     *
     * @param id ID of entity to delete
     */
    void removeById(long id);

}
