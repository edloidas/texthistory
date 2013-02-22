package org.edloidas.web.service.dic.opencorpora;

import org.edloidas.dao.dic.opencorpora.OpcorporaLemmeKeyDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeKey;
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
 * @see org.edloidas.dao.dic.opencorpora.OpcorporaLemmeKeyDao
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaLemmeKey
 */
@Service("opcorporaLemmeKeyService")
public class OpcorporaLemmeKeyService implements EntityService<OpcorporaLemmeKey> {

    /** User DAO, that implements all business logic. */
    @Autowired
    private OpcorporaLemmeKeyDao opcorporaLemmeKeyDao;

    /**
     * Method adds new user.
     *
     * @param o is a {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey to be added.
     */
    @Transactional
    public void addEntity(OpcorporaLemmeKey o) {
        opcorporaLemmeKeyDao.saveEntity(o);
    }

    /**
     * Method updates existed user.
     *
     * @param o is a {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey to be updated.
     */
    @Transactional
    public void updateEntity(OpcorporaLemmeKey o) {
        opcorporaLemmeKeyDao.updateEntity(o);
    }

    /**
     * Method deletes existed user.
     *
     * @param o is a {@code OpcorporaLemmeKey} generic type, that represents OpcorporaLemmeKey to be deleted.
     */
    @Transactional
    public void deleteEntity(OpcorporaLemmeKey o) {
        opcorporaLemmeKeyDao.deleteEntity(o);
    }

    /**
     * Method gets all OpcorporaLemmeKey from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey class to be retrieved.
     *
     * @return {@code List<OpcorporaLemmeKey>}, that represents array of OpcorporaLemmeKey.
     */
    @Transactional
    public List<OpcorporaLemmeKey> getAll(OpcorporaLemmeKey o) {
        return opcorporaLemmeKeyDao.getAll(o);
    }

    /**
     * Method gets OpcorporaLemmeKey with the same 'id'.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code OpcorporaLemmeKey}, that represents retrieved OpcorporaLemmeKey.
     */
    @Transactional
    public OpcorporaLemmeKey getById(OpcorporaLemmeKey o) {
        return opcorporaLemmeKeyDao.getById(o);
    }
}
