package org.edloidas.dao.dic.abramov;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.abramov.AbramovKey;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for AbramovKey entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code AbramovKeyService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.abramov.AbramovKey
 */
@Repository
public class AbramovKeyDao implements CommonDao<AbramovKey> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(AbramovKeyDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves AbramovKey into database.
     *
     * @param o is a {@code AbramovKey}, that represents User to be saved.
     */
    public void saveEntity(AbramovKey o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates AbramovKey in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code AbramovKey}, that represents AbramovKey to be updated.
     */
    public void updateEntity(AbramovKey o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes AbramovKey with current fields values.
     *
     * @param o is a {@code AbramovKey}, that represents AbramovKey to be deleted.
     */
    public void deleteEntity(AbramovKey o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all AbramovKey from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovKey}, that represents table to be retrieved.
     *          It can be any AbramovKey object.
     *
     * @return {@code List<AbramovKey>}, that represents array of all AbramovKey.
     */
    public List<AbramovKey> getAll(AbramovKey o) {
        StringBuilder hql_query;

        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(AbramovKey.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name = \'").append(o.getName()).append("\'");
                hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
            }

            return (List<AbramovKey>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns AbramovKey with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovKey}, that represents AbramovKey with 'id'.
     *
     * @return {@code AbramovKey}, that represents retrieved AbramovKey.
     */
    public AbramovKey getById(AbramovKey o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(AbramovKey.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as a where a.id = \'").append(o.getId()).append("\'");

            return (AbramovKey) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new AbramovKey();
        }
    }
}
