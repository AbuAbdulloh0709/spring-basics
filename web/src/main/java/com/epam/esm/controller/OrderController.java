package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.mapper.impl.OrderMapperImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.hateoas.impl.OrderHateoasAdder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapperImpl mapper;
    private final OrderHateoasAdder hateoasAdder;

    public OrderController(OrderService orderService, UserService userService, OrderMapperImpl mapper, OrderHateoasAdder hateoasAdder) {
        this.orderService = orderService;
        this.userService = userService;
        this.mapper = mapper;
        this.hateoasAdder = hateoasAdder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);

        OrderDto orderDto = mapper.mapToDto(order);
        hateoasAdder.addLinks(orderDto);
        return orderDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderDto order) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        order.setUserId(user.getId());
        Order addedOrder = orderService.insert(mapper.mapToEntity(order));
        OrderDto orderDto = mapper.mapToDto(addedOrder);
        hateoasAdder.addLinks(orderDto);
        return orderDto;
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> ordersByUserId(@PathVariable long userId,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Order> orders = orderService.getOrdersByUserId(userId, page, size);

        return orders.stream()
                .map(mapper::mapToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

}
