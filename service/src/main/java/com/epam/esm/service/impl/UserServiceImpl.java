package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.*;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.IdentifiableValidator;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionMessageKey.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncode;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncode) {
        this.userDao = userDao;

        this.bCryptPasswordEncode = bCryptPasswordEncode;
    }

    @Override
    @Transactional
    public User insert(User user) {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(user, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));

        String userEmail = user.getEmail();
        boolean isUserExist = userDao.findUserByEmail(userEmail).isPresent();
        if (isUserExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.USER_EXIST);
        }
        user.setRole(Role.USER);
        return userDao.insert(user);
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
