package org.edloidas.web.service.dic.abramov;

import org.edloidas.dao.dic.abramov.AbramovWordDao;
import org.edloidas.entity.dic.abramov.AbramovWord;
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
@Service("abramovWordService")
public class AbramovWordService implements EntityService<AbramovWord> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private AbramovWordDao abramovWordDao;

    /**
     * Method adds new AbramovWord.
     *
     * @param o is a {@code AbramovWord}, that represents AbramovWord to be added.
     */
    @Transactional
    public void addEntity(AbramovWord o) {
        abramovWordDao.saveEntity(o);
    }

    /**
     * Method updates existed AbramovWord.
     *
     * @param o is a {@code AbramovWord}, that represents AbramovWord to be updated.
     */
    @Transactional
    public void updateEntity(AbramovWord o) {
        abramovWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed AbramovWord.
     *
     * @param o is a {@code AbramovWord} generic type, that represents AbramovWord to be deleted.
     */
    @Transactional
    public void deleteEntity(AbramovWord o) {
        abramovWordDao.deleteEntity(o);
    }

    /**
     * Method gets all AbramovWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovWord}, that represents AbramovWord class to be retrieved.
     *
     * @return {@code List<AbramovWord>}, that represents array of AbramovWord.
     */
    @Transactional
    public List<AbramovWord> getAll(AbramovWord o) {
        return abramovWordDao.getAll(o);
    }

    /**
     * Method gets AbramovWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code AbramovWord}, that represents retrieved AbramovWord.
     */
    @Transactional
    public AbramovWord getById(AbramovWord o) {
        return abramovWordDao.getById(o);
    }
}
