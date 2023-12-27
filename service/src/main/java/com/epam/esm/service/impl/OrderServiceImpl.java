package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.handler.DateHandler;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.IdentifiableValidator;
import com.epam.esm.validator.OrderValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionMessageKey.*;

@Repository
public class OrderServiceImpl implements OrderService {

    private final DateHandler dateHandler;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    public OrderServiceImpl(DateHandler dateHandler, OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.dateHandler = dateHandler;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validate(order, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<User> optionalUser = userDao.getById(order.getUser().getId());
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException(USER_NOT_FOUND);
        }
        order.setUser(optionalUser.get());

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.getById(order.getGiftCertificate().getId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND);
        }
        order.setGiftCertificate(optionalGiftCertificate.get());

        order.setPrice(optionalGiftCertificate.get().getPrice());
        order.setPurchaseTime(dateHandler.getCurrentDate());
        return orderDao.insert(order);
    }

    @Override
    public Order getById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<Order> optionalOrder = orderDao.getById(id);
        if (optionalOrder.isEmpty()) {
            throw new NoSuchEntityException(ORDER_NOT_FOUND);
        }

        return optionalOrder.get();
    }

    @Override
    public List<Order> getAll(int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        return orderDao.getAll(pageable);
    }

    @Override
    public void removeById(long id) {

    }

    @Override
    public List<Order> getOrdersByUserId(long userId, int page, int size) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateUserId(userId, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Pageable pageRequest = createPageRequest(page, size);

        return orderDao.getByUserId(userId, pageRequest);
    }
}
