package org.edloidas.web.service.common;

import org.edloidas.dao.common.SourceDao;
import org.edloidas.entity.common.Source;
import org.edloidas.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring service for source file database entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * Exceptions are generated automatically, due to transactional annotation.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.dao.common.SourceDao
 * @see org.edloidas.entity.common.Source
 */
@Service("sourceService")
public class SourceService implements EntityService<Source> {

    /** Source DAO, that implements all business logic. */
    @Autowired
    private SourceDao sourceDao;

    /**
     * Method adds new source.
     *
     * @param o is a {@code Source}, that represents source to be added.
     */
    @Transactional
    public void addEntity(Source o) {
        sourceDao.saveEntity(o);
    }

    /**
     * Method updates existed source.
     *
     * @param o is a {@code Source}, that represents source to be updated.
     */
    @Transactional
    public void updateEntity(Source o) {
        sourceDao.updateEntity(o);
    }

    /**
     * Method deletes existed source.
     *
     * @param o is a {@code Source} generic type, that represents source to be deleted.
     */
    @Transactional
    public void deleteEntity(Source o) {
        sourceDao.deleteEntity(o);
    }

    /**
     * Method gets all Sources from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code Source}, that represents Source to be retrieved.
     *
     * @return {@code List<Source>}, that represents array of Sources.
     */
    @Transactional
    public List<Source> getAll(Source o) {
        return sourceDao.getAll(o);
    }

    /**
     * Method gets Source with the same 'id'.
     * If {@code o} has {@code project.id} parameter, it will also be included into select statement.
     *
     * @param o is an {@code Source}, that represents Source with 'id'.
     *
     * @return {@code Source}, that represents retrieved Source.
     */
    @Transactional
    public Source getById(Source o) {
        return sourceDao.getById(o);
    }
}
