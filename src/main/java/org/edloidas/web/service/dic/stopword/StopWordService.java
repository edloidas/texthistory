package org.edloidas.web.service.dic.stopword;

import org.edloidas.dao.dic.stopwords.StopWordDao;
import org.edloidas.entity.dic.stopwords.StopWord;
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
@Service("stopWordService")
public class StopWordService implements EntityService<StopWord> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private StopWordDao stopWordDao;

    /**
     * Method adds new StopWord.
     *
     * @param o is a {@code StopWord}, that represents StopWord to be added.
     */
    @Transactional
    public void addEntity(StopWord o) {
        stopWordDao.saveEntity(o);
    }

    /**
     * Method updates existed StopWord.
     *
     * @param o is a {@code StopWord}, that represents StopWord to be updated.
     */
    @Transactional
    public void updateEntity(StopWord o) {
        stopWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed StopWord.
     *
     * @param o is a {@code StopWord} generic type, that represents StopWord to be deleted.
     */
    @Transactional
    public void deleteEntity(StopWord o) {
        stopWordDao.deleteEntity(o);
    }

    /**
     * Method gets all StopWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code StopWord}, that represents StopWord class to be retrieved.
     *
     * @return {@code List<StopWord>}, that represents array of StopWord.
     */
    @Transactional
    public List<StopWord> getAll(StopWord o) {
        return stopWordDao.getAll(o);
    }

    /**
     * Method gets StopWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code StopWord}, that represents retrieved StopWord.
     */
    @Transactional
    public StopWord getById(StopWord o) {
        return stopWordDao.getById(o);
    }
}
