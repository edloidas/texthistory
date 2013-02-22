package org.edloidas.dao.dic.opencorpora;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeKey;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for OpcorporaLemmeKey entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code OpcorporaLemmeKeyService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaLemmeKey
 */
@Repository
public class OpcorporaLemmeKeyDao implements CommonDao<OpcorporaLemmeKey> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(OpcorporaLemmeKeyDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves OpcorporaLemmeKey into database.
     *
     * @param o is a {@code OpcorporaLemmeKey}, that represents User to be saved.
     */
    public void saveEntity(OpcorporaLemmeKey o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates OpcorporaLemmeKey in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey to be updated.
     */
    public void updateEntity(OpcorporaLemmeKey o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes OpcorporaLemmeKey with current fields values.
     *
     * @param o is a {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey to be deleted.
     */
    public void deleteEntity(OpcorporaLemmeKey o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all OpcorporaLemmeKey from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code OpcorporaLemmeKey}, that represents table to be retrieved.
     *          It can be any OpcorporaLemmeKey object.
     *
     * @return {@code List<OpcorporaLemmeKey>}, that represents array of all OpcorporaLemmeKey.
     */
    public List<OpcorporaLemmeKey> getAll(OpcorporaLemmeKey o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            //hql_query.append("from ").append("opcorpora_lemme_key");
            hql_query.append("from ").append(OpcorporaLemmeKey.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as l where l.name like \'").append(o.getName()).append("%\'");
                //hql_query.append(" as l where l.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getGrammeme() != null && o.getGrammeme().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as l where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" l.opcorpora_grammeme.id = \'").append(o.getGrammeme().getId()).append("\'");
            }

            if (o.getLemme() != null && o.getLemme().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as l where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" l.opcorpora_lemme_key.id = \'").append(o.getLemme().getId()).append("\'");
            }

            return (List<OpcorporaLemmeKey>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns OpcorporaLemmeKey with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaLemmeKey}, that represents OpcorporaLemmeKey with 'id'.
     *
     * @return {@code OpcorporaLemmeKey}, that represents retrieved OpcorporaLemmeKey.
     */
    public OpcorporaLemmeKey getById(OpcorporaLemmeKey o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(OpcorporaLemmeKey.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as l where l.id = \'").append(o.getId()).append("\'");

            return (OpcorporaLemmeKey) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new OpcorporaLemmeKey();
        }
    }
}
