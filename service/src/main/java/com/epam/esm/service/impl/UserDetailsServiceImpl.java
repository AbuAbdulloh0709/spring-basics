package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.ExceptionMessageKey;
import com.epam.esm.exceptions.ExceptionResult;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Method for loading users by user's email.
     *
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validateEmail(email, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<User> user = userDao.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND);
        }

        return user.get();
    }
}
