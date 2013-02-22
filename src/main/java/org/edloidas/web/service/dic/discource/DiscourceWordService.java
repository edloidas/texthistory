package org.edloidas.web.service.dic.discource;

import org.edloidas.dao.dic.discource.DiscourceWordDao;
import org.edloidas.entity.dic.discource.DiscourceWord;
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
 * @see org.edloidas.dao.dic.discource.DiscourceWordDao
 * @see org.edloidas.entity.dic.discource.DiscourceWord
 */
@Service("discourceWordService")
public class DiscourceWordService implements EntityService<DiscourceWord> {

    /** DiscourceWord DAO, that implements all business logic. */
    @Autowired
    private DiscourceWordDao discourceWordDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code DiscourceWord}, that represents DiscourceWord to be added.
     */
    @Transactional
    public void addEntity(DiscourceWord o) {
        discourceWordDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code DiscourceWord}, that represents DiscourceWord to be updated.
     */
    @Transactional
    public void updateEntity(DiscourceWord o) {
        discourceWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code DiscourceWord} generic type, that represents DiscourceWord to be deleted.
     */
    @Transactional
    public void deleteEntity(DiscourceWord o) {
        discourceWordDao.deleteEntity(o);
    }

    /**
     * Method gets all DiscourceWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code DiscourceWord}, that represents DiscourceWord class to be retrieved.
     *
     * @return {@code List<DiscourceWord>}, that represents array of DiscourceWord.
     */
    @Transactional
    public List<DiscourceWord> getAll(DiscourceWord o) {
        return discourceWordDao.getAll(o);
    }

    /**
     * Method gets DiscourceWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code DiscourceWord}, that represents retrieved DiscourceWord.
     */
    @Transactional
    public DiscourceWord getById(DiscourceWord o) {
        return discourceWordDao.getById(o);
    }
}
