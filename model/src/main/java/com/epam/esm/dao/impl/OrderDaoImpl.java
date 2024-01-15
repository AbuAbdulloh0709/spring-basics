package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String QUERY_GET_ORDER_ALL = "select o from Order as o";
    private static final String QUERY_SELECT_BY_USER_ID = "select o from Order o where o.user.id = :userId";
    private static final String QUERY_SELECT_BY_GC_ID = "select o from Order o where o.giftCertificate.id = :gcId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order insert(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Optional<Order> getById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> getAll(Pageable pageable) {
        return entityManager.createQuery(QUERY_GET_ORDER_ALL, Order.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getByUserId(long userId, Pageable pageable) {
        return entityManager.createQuery(QUERY_SELECT_BY_USER_ID, Order.class)
                .setParameter("userId", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public boolean ordersHasGiftCertificateByGiftCertificateID(long gcId) {
        return entityManager.createQuery(QUERY_SELECT_BY_GC_ID, Order.class)
                .setParameter("gcId", gcId)
                .getResultList().stream()
                .findFirst().isPresent();
    }
}
