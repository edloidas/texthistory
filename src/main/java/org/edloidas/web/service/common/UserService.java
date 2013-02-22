package org.edloidas.web.service.common;

import org.edloidas.dao.common.UserDao;
import org.edloidas.entity.common.User;
import org.edloidas.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring service for user database entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * Exceptions are generated automatically, due to transactional annotation.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.dao.common.UserDao
 * @see org.edloidas.entity.common.User
 */
@Service("userService")
public class UserService implements EntityService<User> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private UserDao userDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code User}, that represents user to be added.
     */
    @Transactional
    public void addEntity(User o) {
        userDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code User}, that represents user to be updated.
     */
    @Transactional
    public void updateEntity(User o) {
        userDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code User} generic type, that represents user to be deleted.
     */
    @Transactional
    public void deleteEntity(User o) {
        userDao.deleteEntity(o);
    }

    /**
     * Method gets all users from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code User}, that represents User class to be retrieved.
     *
     * @return {@code List<User>}, that represents array of users.
     */
    @Transactional
    public List<User> getAll(User o) {
        return userDao.getAll(o);
    }

    /**
     * Method gets user with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code User}, that represents retrieved user.
     */
    @Transactional
    public User getById(User o) {
        return userDao.getById(o);
    }
}
