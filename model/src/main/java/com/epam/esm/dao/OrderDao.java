package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    List<Order> getByUserId(long userId, Pageable pageable);
}
