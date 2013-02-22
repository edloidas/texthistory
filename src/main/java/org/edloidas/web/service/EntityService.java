package org.edloidas.web.service;

import java.util.List;

/**
 * Common service for all database entities.
 * Provides 'CRUD' (create, read, update, delete) functions.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.dao.CommonDao
 */
public interface EntityService<T> {

    /**
     * Method adds new entity.
     *
     * @param o is a {@code T} generic type, that represents entity to be added.
     */
    public void addEntity(T o);

    /**
     * Method updates existed entity.
     *
     * @param o is a {@code T} generic type, that represents entity to be updated.
     */
    public void updateEntity(T o);

    /**
     * Method deletes existed entity.
     *
     * @param o is a {@code T} generic type, that represents entity to be deleted.
     */
    public void deleteEntity(T o);

    /**
     * Method gets all entities of class {@code T} from database.
     *
     * @param o is an {@code T} generic type, that represents object of
     *          entity class to be retrieved.
     *
     * @return {@code List<T>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    public List<T> getAll(T o);

    /**
     * Method gets entity with the same 'id'.
     * If {@code o} has other parameters, they should also be included into select statement.
     *
     * @param o is an {@code int}, that represents entity's 'id'.
     *
     * @return {@code T} generic type, that represents retrieved entity.
     */
    public T getById(T o);
}
