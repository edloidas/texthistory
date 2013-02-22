package org.edloidas.dao;

import java.util.List;

/**
 * Common interface for all database DAOs.
 * Provides 'CRUD' (create, read, update, delete) functions.
 *
 * @author edloidas@gmail.com
 */
public interface CommonDao<T> {
    /**
     * Method saves object into database.
     *
     * @param o is a {@code T} generic type, that represents entity to be saved.
     */
    public void saveEntity(T o);

    /**
     * Method updates object i database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code T} generic type, that represents entity to be updated.
     */
    public void updateEntity(T o);

    /**
     * Method deletes object with current field value.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code T} generic type, that represents entity to be deleted.
     */
    public void deleteEntity(T o);

    /**
     * Method return all objects from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code T} generic type, that represents entity to be compared.
     *
     * @return {@code List<T>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    public List<T> getAll(T o);

    /**
     * Method returns object with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code T}, that represents object with 'id'.
     *
     * @return {@code T} generic type, that represents retrieved object.
     */
    public T getById(T o);
}
