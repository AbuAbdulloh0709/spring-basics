package com.epam.esm.service;

import com.epam.esm.exceptions.ExceptionMessageKey;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface BaseService<T> {

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
     * @return Entity object
     */
    T getById(long id);

    /**
     * Method for getting all entities.
     *
     * @param page page number for pagination
     * @param size page size for pagination
     * @return List of all entities
     */

    List<T> getAll(int page, int size);

    /**
     * Method for deleting entity.
     *
     * @param id ID of entity to delete
     */
    void removeById(long id);

    /**
     * Default method for creating page request.
     *
     * @param page the page number
     * @param size the size of each page
     * @return Pageable object
     */

    default Pageable createPageRequest(int page, int size) {
        Pageable pageRequest;
        try {
            pageRequest = PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            ExceptionResult exceptionResult = new ExceptionResult();
            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page, size);
            throw new IncorrectParameterException(exceptionResult);
        }
        return pageRequest;
    }

    /**
     * Default method for extracting single parameter of the request.
     *
     * @param requestParams the request parameters
     * @param parameter which intended to extract
     * @return String object
     */

    default String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }

}
