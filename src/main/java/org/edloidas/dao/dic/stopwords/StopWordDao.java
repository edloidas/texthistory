package org.edloidas.dao.dic.stopwords;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.dic.stopwords.StopWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Project entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code ProjectService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.common.Project
 */
@Repository
public class StopWordDao implements CommonDao<StopWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(StopWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves StopWord into database.
     *
     * @param o is a {@code StopWord}, that represents StopWord to be saved.
     */
    public void saveEntity(StopWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates StopWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code StopWord}, that represents StopWord to be updated.
     */
    public void updateEntity(StopWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes StopWord with current fields values.
     *
     * @param o is a {@code StopWord}, that represents entity to be deleted.
     */
    public void deleteEntity(StopWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all StopWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement, except {@code description}.
     *
     * @param o is an {@code StopWord}, that represents table to be retrieved.
     *          It can be any StopWord object, thus only class name is used.
     *
     * @return {@code List<StopWord>}, that represents array of all StopWord.
     */
    public List<StopWord> getAll(StopWord o) {
        StringBuilder hql_query;

        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(StopWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as s where s.name = \'").append(o.getName()).append("\'");
            }

            return (List<StopWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all StopWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code StopWord} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<StopWord>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<StopWord> getAll(StopWord o, int limit) {
        StringBuilder hql_query;

        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(StopWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as s where s.name = \'").append(o.getName()).append("%\'");
            }

            return (List<StopWord>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns StopWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code StopWord}, that represents StopWord with 'id'.
     *
     * @return {@code StopWord}, that represents retrieved StopWord.
     */
    public StopWord getById(StopWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(Project.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as s where s.id = \'").append(o.getId()).append("\'");

            return (StopWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new StopWord();
        }
    }
}
