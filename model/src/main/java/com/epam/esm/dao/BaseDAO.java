package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;

import java.util.List;

public interface BaseDAO<T> {

    void insert(T t) throws DaoException;

    T getById(long id) throws DaoException;

    List<T> getAll() throws DaoException;

    void removeById(long id) throws DaoException;

}
