package com.epam.esm.dto.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.mapper.Mapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements Mapper<Order, OrderDto> {
    @Override
    public Order mapToEntity(OrderDto dto) {
        Order order = new Order();

        order.setId(dto.getId());
        order.setPrice(dto.getPrice());
        order.setPurchaseTime(dto.getPurchaseTime());

        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(dto.getGiftCertificateId());
        order.setGiftCertificate(giftCertificate);

        return order;
    }

    @Override
    public OrderDto mapToDto(Order entity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(entity.getId());
        orderDto.setPrice(entity.getPrice());
        orderDto.setPurchaseTime(entity.getPurchaseTime());
        orderDto.setUserId(entity.getUser().getId());
        orderDto.setGiftCertificateId(entity.getGiftCertificate().getId());

        return orderDto;
    }
}
