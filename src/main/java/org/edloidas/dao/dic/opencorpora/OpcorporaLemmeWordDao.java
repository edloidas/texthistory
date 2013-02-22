package org.edloidas.dao.dic.opencorpora;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for OpcorporaLemmeWord entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code OpcorporaLemmeWordService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaLemmeWord
 */
@Repository
public class OpcorporaLemmeWordDao implements CommonDao<OpcorporaLemmeWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(OpcorporaLemmeWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves OpcorporaLemmeWord into database.
     *
     * @param o is a {@code OpcorporaLemmeWord}, that represents User to be saved.
     */
    public void saveEntity(OpcorporaLemmeWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates OpcorporaLemmeWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord to be updated.
     */
    public void updateEntity(OpcorporaLemmeWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes OpcorporaLemmeWord with current fields values.
     *
     * @param o is a {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord to be deleted.
     */
    public void deleteEntity(OpcorporaLemmeWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all OpcorporaLemmeWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code OpcorporaLemmeWord}, that represents table to be retrieved.
     *          It can be any OpcorporaLemmeWord object.
     *
     * @return {@code List<OpcorporaLemmeWord>}, that represents array of all OpcorporaLemmeWord.
     */
    public List<OpcorporaLemmeWord> getAll(OpcorporaLemmeWord o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append("opcorpora_lemme_word");
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as l where l.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getKey() != null && o.getKey().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as l where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" l.opcorpora_lemme_key.id = \'").append(o.getKey().getId()).append("\'");
            }

            return (List<OpcorporaLemmeWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns OpcorporaLemmeWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaLemmeWord}, that represents OpcorporaLemmeWord with 'id'.
     *
     * @return {@code OpcorporaLemmeWord}, that represents retrieved OpcorporaLemmeWord.
     */
    public OpcorporaLemmeWord getById(OpcorporaLemmeWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append("opcorpora_lemme_word");
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as l where l.id = \'").append(o.getId()).append("\'");

            return (OpcorporaLemmeWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new OpcorporaLemmeWord();
        }
    }
}
