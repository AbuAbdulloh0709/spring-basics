package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    /**
     * Method for getting entity objects by userId.
     *
     * @param userId ID of entity to get
     * @param pageable Pageable object
     * @return List of Entities
     */
    List<Order> getByUserId(long userId, Pageable pageable);
}
