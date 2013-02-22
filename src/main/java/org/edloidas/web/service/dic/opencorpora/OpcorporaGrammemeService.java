package org.edloidas.web.service.dic.opencorpora;

import org.edloidas.dao.dic.opencorpora.OpcorporaGrammemeDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaGrammeme;
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
 * @see org.edloidas.dao.dic.opencorpora.OpcorporaGrammemeDao
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaGrammeme
 */
@Service("opcorporaGrammemeService")
public class OpcorporaGrammemeService implements EntityService<OpcorporaGrammeme> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private OpcorporaGrammemeDao opcorporaGrammemeDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme to be added.
     */
    @Transactional
    public void addEntity(OpcorporaGrammeme o) {
        opcorporaGrammemeDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme to be updated.
     */
    @Transactional
    public void updateEntity(OpcorporaGrammeme o) {
        opcorporaGrammemeDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code OpcorporaGrammeme} generic type, that represents OpcorporaGrammeme to be deleted.
     */
    @Transactional
    public void deleteEntity(OpcorporaGrammeme o) {
        opcorporaGrammemeDao.deleteEntity(o);
    }

    /**
     * Method gets all OpcorporaGrammeme from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme class to be retrieved.
     *
     * @return {@code List<OpcorporaGrammeme>}, that represents array of OpcorporaGrammeme.
     */
    @Transactional
    public List<OpcorporaGrammeme> getAll(OpcorporaGrammeme o) {
        return opcorporaGrammemeDao.getAll(o);
    }

    /**
     * Method gets OpcorporaGrammeme with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code OpcorporaGrammeme}, that represents retrieved OpcorporaGrammeme.
     */
    @Transactional
    public OpcorporaGrammeme getById(OpcorporaGrammeme o) {
        return opcorporaGrammemeDao.getById(o);
    }
}
