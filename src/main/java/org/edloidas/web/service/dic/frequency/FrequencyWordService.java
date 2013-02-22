package org.edloidas.web.service.dic.frequency;

import org.edloidas.dao.dic.frequency.FrequencyWordDao;
import org.edloidas.entity.dic.frequency.FrequencyWord;
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
 * @see org.edloidas.dao.dic.frequency.FrequencyWordDao
 * @see org.edloidas.entity.dic.frequency.FrequencyWord
 */
@Service("frequencyWordService")
public class FrequencyWordService implements EntityService<FrequencyWord> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private FrequencyWordDao frequencyWordDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code FrequencyWord}, that represents FrequencyWord to be added.
     */
    @Transactional
    public void addEntity(FrequencyWord o) {
        frequencyWordDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code FrequencyWord}, that represents FrequencyWord to be updated.
     */
    @Transactional
    public void updateEntity(FrequencyWord o) {
        frequencyWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code FrequencyWord} generic type, that represents FrequencyWord to be deleted.
     */
    @Transactional
    public void deleteEntity(FrequencyWord o) {
        frequencyWordDao.deleteEntity(o);
    }

    /**
     * Method gets all FrequencyWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code FrequencyWord}, that represents FrequencyWord class to be retrieved.
     *
     * @return {@code List<FrequencyWord>}, that represents array of FrequencyWord.
     */
    @Transactional
    public List<FrequencyWord> getAll(FrequencyWord o) {
        return frequencyWordDao.getAll(o);
    }

    /**
     * Method gets FrequencyWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code FrequencyWord}, that represents retrieved FrequencyWord.
     */
    @Transactional
    public FrequencyWord getById(FrequencyWord o) {
        return frequencyWordDao.getById(o);
    }
}
