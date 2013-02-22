package org.edloidas.web.service.dic.opencorpora;

import org.edloidas.dao.dic.opencorpora.OpcorporaLemmeWordDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeWord;
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
 * @see org.edloidas.dao.dic.opencorpora.OpcorporaLemmeWordDao
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaLemmeWord
 */
@Service("opcorporaLemmeWordService")
public class OpcorporaLemmeWordService implements EntityService<OpcorporaLemmeWord> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private OpcorporaLemmeWordDao opcorporaLemmeWordDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord to be added.
     */
    @Transactional
    public void addEntity(OpcorporaLemmeWord o) {
        opcorporaLemmeWordDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord to be updated.
     */
    @Transactional
    public void updateEntity(OpcorporaLemmeWord o) {
        opcorporaLemmeWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code OpcorporaLemmeWord} generic type, that represents OpcorporaLemmeWord to be deleted.
     */
    @Transactional
    public void deleteEntity(OpcorporaLemmeWord o) {
        opcorporaLemmeWordDao.deleteEntity(o);
    }

    /**
     * Method gets all OpcorporaLemmeWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord class to be retrieved.
     *
     * @return {@code List<OpcorporaLemmeWord>}, that represents array of OpcorporaLemmeWord.
     */
    @Transactional
    public List<OpcorporaLemmeWord> getAll(OpcorporaLemmeWord o) {
        return opcorporaLemmeWordDao.getAll(o);
    }

    /**
     * Method gets OpcorporaLemmeWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code OpcorporaLemmeWord}, that represents retrieved OpcorporaLemmeWord.
     */
    @Transactional
    public OpcorporaLemmeWord getById(OpcorporaLemmeWord o) {
        return opcorporaLemmeWordDao.getById(o);
    }
}
