package org.edloidas.web.service.dic.abramov;

import org.edloidas.dao.dic.abramov.AbramovKeyDao;
import org.edloidas.entity.dic.abramov.AbramovKey;
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
@Service("abramovKeyService")
public class AbramovKeyService implements EntityService<AbramovKey> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private AbramovKeyDao abramovKeyDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code AbramovKey}, that represents AbramovKey to be added.
     */
    @Transactional
    public void addEntity(AbramovKey o) {
        abramovKeyDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code AbramovKey}, that represents AbramovKey to be updated.
     */
    @Transactional
    public void updateEntity(AbramovKey o) {
        abramovKeyDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code AbramovKey} generic type, that represents AbramovKey to be deleted.
     */
    @Transactional
    public void deleteEntity(AbramovKey o) {
        abramovKeyDao.deleteEntity(o);
    }

    /**
     * Method gets all AbramovKey from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovKey}, that represents AbramovKey class to be retrieved.
     *
     * @return {@code List<AbramovKey>}, that represents array of AbramovKey.
     */
    @Transactional
    public List<AbramovKey> getAll(AbramovKey o) {
        return abramovKeyDao.getAll(o);
    }

    /**
     * Method gets AbramovKey with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code User}, that represents retrieved AbramovKey.
     */
    @Transactional
    public AbramovKey getById(AbramovKey o) {
        return abramovKeyDao.getById(o);
    }
}
