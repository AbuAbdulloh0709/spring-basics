package com.epam.esm.service;

import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.util.List;

public interface BaseService<T> {
    void insert(T t) throws DaoException, IncorrectParameterException;

    T getById(long id) throws DaoException, IncorrectParameterException;

    List<T> getAll() throws DaoException, IncorrectParameterException;

    void removeById(long id) throws DaoException, IncorrectParameterException;
}
