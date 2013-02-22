package org.edloidas.dao.dic.abramov;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.abramov.AbramovWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for AbramovWord entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code AbramovWordService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.abramov.AbramovWord
 */
@Repository
public class AbramovWordDao implements CommonDao<AbramovWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(AbramovWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves AbramovWord into database.
     *
     * @param o is a {@code AbramovWord}, that represents AbramovWord to be saved.
     */
    public void saveEntity(AbramovWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates AbramovWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code AbramovWord}, that represents AbramovWord to be updated.
     */
    public void updateEntity(AbramovWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes AbramovWord with current fields values.
     *
     * @param o is a {@code AbramovWord}, that represents entity to be deleted.
     */
    public void deleteEntity(AbramovWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all AbramovWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovWord}, that represents table to be retrieved.
     *          It can be any AbramovWord object.
     *
     * @return {@code List<AbramovWord>}, that represents array of all AbramovWord.
     */
    public List<AbramovWord> getAll(AbramovWord o) {
        StringBuilder hql_query;
        boolean multiple; // determines if there is already 'where'

        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(AbramovWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as a where a.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getKey() != null && o.getKey().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as a where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" a.abramov_key.id = \'").append(o.getKey().getId()).append("\'");
            }

            return (List<AbramovWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns AbramovWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code AbramovWord}, that represents AbramovWord with 'id'.
     *
     * @return {@code AbramovWord}, that represents retrieved AbramovWord.
     */
    public AbramovWord getById(AbramovWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(AbramovWord.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as a where a.id = \'").append(o.getId()).append("\'");

            if (o.getKey() != null && o.getKey().getId() != 0) {
                hql_query.append(" and a.key.id = \'").append(o.getKey().getId()).append("\'");
            }

            return (AbramovWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new AbramovWord();
        }
    }
}
