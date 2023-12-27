package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<Order>{
    List<Order> getOrdersByUserId (long userId, int page, int size);
}
