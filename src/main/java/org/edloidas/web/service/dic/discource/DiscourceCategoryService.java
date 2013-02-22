package org.edloidas.web.service.dic.discource;

import org.edloidas.dao.dic.discource.DiscourceCategoryDao;
import org.edloidas.entity.dic.discource.DiscourceCategory;
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
 * @see org.edloidas.dao.dic.discource.DiscourceCategoryDao
 * @see org.edloidas.entity.dic.discource.DiscourceCategory
 */
@Service("discourceCategoryService")
public class DiscourceCategoryService implements EntityService<DiscourceCategory> {

    /** DiscourceCategory DAO, that implements all business logic. */
    @Autowired
    private DiscourceCategoryDao discourceCategoryDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code DiscourceCategory}, that represents DiscourceCategory to be added.
     */
    @Transactional
    public void addEntity(DiscourceCategory o) {
        discourceCategoryDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code DiscourceCategory}, that represents DiscourceCategory to be updated.
     */
    @Transactional
    public void updateEntity(DiscourceCategory o) {
        discourceCategoryDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code DiscourceCategory} generic type, that represents DiscourceCategory to be deleted.
     */
    @Transactional
    public void deleteEntity(DiscourceCategory o) {
        discourceCategoryDao.deleteEntity(o);
    }

    /**
     * Method gets all DiscourceCategory from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code DiscourceCategory}, that represents DiscourceCategory class to be retrieved.
     *
     * @return {@code List<DiscourceCategory>}, that represents array of DiscourceCategory.
     */
    @Transactional
    public List<DiscourceCategory> getAll(DiscourceCategory o) {
        return discourceCategoryDao.getAll(o);
    }

    /**
     * Method gets DiscourceCategory with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code DiscourceCategory}, that represents retrieved DiscourceCategory.
     */
    @Transactional
    public DiscourceCategory getById(DiscourceCategory o) {
        return discourceCategoryDao.getById(o);
    }
}
