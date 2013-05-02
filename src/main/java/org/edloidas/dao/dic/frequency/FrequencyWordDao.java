package org.edloidas.dao.dic.frequency;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.frequency.FrequencyWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for FrequencyWord entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code FrequencyWordService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.frequency.FrequencyWord
 */
@Repository
public class FrequencyWordDao implements CommonDao<FrequencyWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(FrequencyWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves FrequencyWord into database.
     *
     * @param o is a {@code FrequencyWord}, that represents User to be saved.
     */
    public void saveEntity(FrequencyWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates FrequencyWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code FrequencyWord}, that represents FrequencyWord to be updated.
     */
    public void updateEntity(FrequencyWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes FrequencyWord with current fields values.
     *
     * @param o is a {@code FrequencyWord}, that represents FrequencyWord to be deleted.
     */
    public void deleteEntity(FrequencyWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all FrequencyWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code FrequencyWord}, that represents table to be retrieved.
     *          It can be any FrequencyWord object.
     *
     * @return {@code List<FrequencyWord>}, that represents array of all FrequencyWord.
     */
    public List<FrequencyWord> getAll(FrequencyWord o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(FrequencyWord.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as f where f.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getFreq() != 0.0d) {
                if (!multiple) {
                    hql_query.append(" as f where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" f.freq = \'").append(o.getFreq()).append("\'");
            }

            return (List<FrequencyWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all FrequencyWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code FrequencyWord} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<FrequencyWord>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<FrequencyWord> getAll(FrequencyWord o, int limit) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(FrequencyWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as f where f.name like \'").append(o.getName()).append("%\'");
                multiple = true;
            }

            if (o.getFreq() != 0.0d) {
                if (!multiple) {
                    hql_query.append(" as f where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" f.freq = \'").append(o.getFreq()).append("\'");
            }

            return (List<FrequencyWord>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns FrequencyWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code FrequencyWord}, that represents FrequencyWord with 'id'.
     *
     * @return {@code FrequencyWord}, that represents retrieved FrequencyWord.
     */
    public FrequencyWord getById(FrequencyWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(FrequencyWord.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as f where f.id = \'").append(o.getId()).append("\'");

            return (FrequencyWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new FrequencyWord();
        }
    }
}
