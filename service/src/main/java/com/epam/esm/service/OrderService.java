package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<Order>{

    /**
     * Method for retrieving orders by user's ID.
     *
     * @param userId ID of user
     * @param page the number of page
     * @param size the size of each page
     * @return List of Orders
     */
    List<Order> getOrdersByUserId (long userId, int page, int size);
}
