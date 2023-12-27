package com.epam.esm.service;

import com.epam.esm.exceptions.ExceptionMessageKey;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface BaseService<T> {
    T insert(T t);

    T getById(long id);

    List<T> getAll(int page, int size);

    void removeById(long id);

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

    default String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }

}
