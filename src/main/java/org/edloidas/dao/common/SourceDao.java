package org.edloidas.dao.common;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.common.Source;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Source entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code SourceService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.common.Source
 */
@Repository
public class SourceDao implements CommonDao<Source> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(SourceDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves Source into database.
     *
     * @param o is a {@code Source}, that represents Source to be saved.
     */
    public void saveEntity(Source o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates Source in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code Source}, that represents Source to be updated.
     */
    public void updateEntity(Source o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes Source with current fields values.
     *
     * @param o is a {@code Source}, that represents Source to be deleted.
     */
    public void deleteEntity(Source o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all Sources from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement, except {@code text}.
     *
     * @param o is an {@code Source}, that represents table to be retrieved.
     *          It can be any Source object, thus only class name is used.
     *
     * @return {@code List<Source>}, that represents array of all Sources.
     */
    public List<Source> getAll(Source o) {
        StringBuilder hql_query;
        boolean multiple; // determines if there is already 'where'

        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(Source.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as s where s.name = \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getUploaded() != null) {
                if (!multiple) {
                    hql_query.append(" as s where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" s.uploaded = \'").append(o.getUploaded()).append("\'");
            }

            if (o.getMd5() != null) {
                if (!multiple) {
                    hql_query.append(" as s where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" s.md5 = \'").append(o.getMd5()).append("\'");
            }

            if (o.getProject() != null && o.getProject().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as s where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" s.project.id = \'").append(o.getProject().getId()).append("\'");
            }

            return (List<Source>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns Source with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code Source}, that represents Source with 'id'.
     *
     * @return {@code Source}, that represents retrieved Source.
     */
    public Source getById(Source o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(Source.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as s where s.id = \'").append(o.getId()).append("\'");

            if (o.getProject() != null && o.getProject().getId() != 0) {
                hql_query.append(" and s.project.id = \'").append(o.getProject().getId()).append("\'");
            }

            return (Source) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new Source();
        }
    }
}
