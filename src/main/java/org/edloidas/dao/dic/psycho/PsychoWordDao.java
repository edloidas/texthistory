package org.edloidas.dao.dic.psycho;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.psycho.PsychoWord;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for PsychoWord entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code PsychoWordService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.psycho.PsychoWord
 */
@Repository
public class PsychoWordDao implements CommonDao<PsychoWord> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(PsychoWordDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves PsychoWord into database.
     *
     * @param o is a {@code PsychoWord}, that represents PsychoWord to be saved.
     */
    public void saveEntity(PsychoWord o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates PsychoWord in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code PsychoWord}, that represents PsychoWord to be updated.
     */
    public void updateEntity(PsychoWord o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes PsychoWord with current fields values.
     *
     * @param o is a {@code PsychoWord}, that represents PsychoWord to be deleted.
     */
    public void deleteEntity(PsychoWord o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all PsychoWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code PsychoWord}, that represents table to be retrieved.
     *          It can be any PsychoWord object.
     *
     * @return {@code List<PsychoWord>}, that represents array of all PsychoWord.
     */
    public List<PsychoWord> getAll(PsychoWord o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(PsychoWord.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as p where p.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getDescription() != null) {
                if (!multiple) {
                    hql_query.append(" as p where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" p.description like \'").append(o.getDescription()).append("\'");
            }

            return (List<PsychoWord>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all PsychoWord from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code PsychoWord} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<PsychoWord>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<PsychoWord> getAll(PsychoWord o, int limit) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(PsychoWord.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                multiple = true;
            }

            if (o.getDescription() != null) {
                if (!multiple) {
                    hql_query.append(" as p where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" p.description like \'").append(o.getDescription()).append("\'");
            }

            return (List<PsychoWord>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns PsychoWord with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code PsychoWord}, that represents PsychoWord with 'id'.
     *
     * @return {@code PsychoWord}, that represents retrieved PsychoWord.
     */
    public PsychoWord getById(PsychoWord o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(PsychoWord.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as p where p.id = \'").append(o.getId()).append("\'");

            return (PsychoWord) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new PsychoWord();
        }
    }
}
