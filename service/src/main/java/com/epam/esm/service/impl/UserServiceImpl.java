package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.IdentifiableValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionMessageKey.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User insert(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<User> optionalUser = userDao.getById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException(USER_NOT_FOUND);
        }

        return optionalUser.get();
    }

    @Override
    public List<User> getAll(int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        return userDao.getAll(pageable);
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }
}
