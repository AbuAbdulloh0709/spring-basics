package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.handler.DateHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDaoImpl orderDao = Mockito.mock(OrderDaoImpl.class);

    @Mock
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @Mock
    private DateHandler dateHandler = Mockito.mock(DateHandler.class);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2021-08-19T06:12:15.156");

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Order ORDER_1 = new Order(1, new BigDecimal("15.2"), UPDATED_DATE, new User(1, "name1"),
            new GiftCertificate(1, "giftCertificate1", "description1", new BigDecimal("10.1"),
                    1, LocalDateTime.parse("2021-08-29T06:12:15"), LocalDateTime.parse("2021-08-29T06:12:15"), null));
    private static final Order ORDER_2 = new Order(2, new BigDecimal("30.4"), UPDATED_DATE, new User(1, "name1"),
            new GiftCertificate(2, "giftCertificate3", "description3", new BigDecimal("30.3"),
                    3, LocalDateTime.parse("2023-08-29T06:12:15"), LocalDateTime.parse("2023-08-29T06:12:15"), null));

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(orderDao.getById(ORDER_1.getId())).thenReturn(Optional.of(ORDER_1));

        Order actual = orderService.getById(ORDER_1.getId());
        Order expected = ORDER_1;

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert() {
        Order insertableOrder = new Order();
        insertableOrder.setPrice(ORDER_1.getPrice());
        insertableOrder.setGiftCertificate(ORDER_1.getGiftCertificate());
        insertableOrder.setPurchaseTime(ORDER_1.getPurchaseTime());
        insertableOrder.setUser(ORDER_1.getUser());
        doReturn(ORDER_1).when(orderDao).insert(any());
        when(dateHandler.getCurrentDate()).thenReturn(UPDATED_DATE);
        when(userDao.getById(ORDER_1.getUser().getId())).thenReturn(Optional.of(new User()));
        when(giftCertificateDao.getById(ORDER_1.getGiftCertificate().getId())).thenReturn(Optional.of(new GiftCertificate()));

        Order actual = orderService.insert(insertableOrder);
        Order expected = ORDER_1;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersByUserId() {
        List<Order> orders = Arrays.asList(ORDER_1, ORDER_2);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(orderDao.getByUserId(ORDER_1.getUser().getId(), pageRequest)).thenReturn(orders);

        List<Order> actual = orderService.getOrdersByUserId(ORDER_1.getUser().getId(), PAGE, SIZE);
        List<Order> expected = orders;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        List<Order> orders = Arrays.asList(ORDER_1,ORDER_2);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(orderDao.getAll(pageRequest)).thenReturn(orders);

        List<Order> actual = orderService.getAll(PAGE, SIZE);
        List<Order> expected = orders;

        assertEquals(expected, actual);
    }
}