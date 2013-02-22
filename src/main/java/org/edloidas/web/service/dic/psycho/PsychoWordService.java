package org.edloidas.web.service.dic.psycho;

import org.edloidas.dao.dic.psycho.PsychoWordDao;
import org.edloidas.entity.dic.psycho.PsychoWord;
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
 * @see org.edloidas.dao.dic.psycho.PsychoWordDao
 * @see org.edloidas.entity.dic.psycho.PsychoWord
 */
@Service("psychoWordService")
public class PsychoWordService implements EntityService<PsychoWord> {

    /** PsychoWord DAO, that implements all business logic. */
    @Autowired
    private PsychoWordDao psychoWordDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code PsychoWord}, that represents PsychoWord to be added.
     */
    @Transactional
    public void addEntity(PsychoWord o) {
        psychoWordDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code PsychoWord}, that represents PsychoWord to be updated.
     */
    @Transactional
    public void updateEntity(PsychoWord o) {
        psychoWordDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code PsychoWord} generic type, that represents PsychoWord to be deleted.
     */
    @Transactional
    public void deleteEntity(PsychoWord o) {
        psychoWordDao.deleteEntity(o);
    }

    /**
     * Method gets all PsychoWord from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code PsychoWord}, that represents PsychoWord class to be retrieved.
     *
     * @return {@code List<PsychoWord>}, that represents array of PsychoWord.
     */
    @Transactional
    public List<PsychoWord> getAll(PsychoWord o) {
        return psychoWordDao.getAll(o);
    }

    /**
     * Method gets PsychoWord with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code PsychoWord}, that represents retrieved PsychoWord.
     */
    @Transactional
    public PsychoWord getById(PsychoWord o) {
        return psychoWordDao.getById(o);
    }
}
