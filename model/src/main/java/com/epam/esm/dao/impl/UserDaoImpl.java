package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final String QUERY_GET_USER_ALL = "select u from User as u";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User insert(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll(Pageable pageable) {
        return entityManager.createQuery(QUERY_GET_USER_ALL, User.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }
}
