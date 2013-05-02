package org.edloidas.dao.dic.discource;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.discource.DiscourceWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for DiscourceWord entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code DiscourceWordService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.discource.DiscourceWord
 */
@Repository
public class DiscourceWordDao implements CommonDao<DiscourceWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(DiscourceWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves DiscourceWord into database.
     *
     * @param o is a {@code DiscourceWord}, that represents DiscourceWord to be saved.
     */
    public void saveEntity(DiscourceWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates DiscourceWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code DiscourceWord}, that represents DiscourceWord to be updated.
     */
    public void updateEntity(DiscourceWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes DiscourceWord with current fields values.
     *
     * @param o is a {@code DiscourceWord}, that represents DiscourceWord to be deleted.
     */
    public void deleteEntity(DiscourceWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all DiscourceWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code DiscourceWord}, that represents table to be retrieved.
     *          It can be any DiscourceWord object.
     *
     * @return {@code List<DiscourceWord>}, that represents array of all DiscourceWord.
     */
    public List<DiscourceWord> getAll(DiscourceWord o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(DiscourceWord.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as d where d.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getCategory() != null && o.getCategory().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as d where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.discource_category.id = \'").append(o.getCategory().getId()).append("\'");
            }

            return (List<DiscourceWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all DiscourceWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code DiscourceWord} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<DiscourceWord>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<DiscourceWord> getAll(DiscourceWord o, int limit) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(DiscourceWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as d where d.name like \'").append(o.getName()).append("%\'");
                multiple = true;
            }

            if (o.getCategory() != null && o.getCategory().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as d where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.discource_category.id = \'").append(o.getCategory().getId()).append("\'");
            }

            return (List<DiscourceWord>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns DiscourceWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code DiscourceWord}, that represents DiscourceWord with 'id'.
     *
     * @return {@code DiscourceWord}, that represents retrieved DiscourceWord.
     */
    public DiscourceWord getById(DiscourceWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(DiscourceWord.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as d where d.id = \'").append(o.getId()).append("\'");

            return (DiscourceWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new DiscourceWord();
        }
    }
}
