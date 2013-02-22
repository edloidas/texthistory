package org.edloidas.dao.common;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.common.Project;
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
public class ProjectDao implements CommonDao<Project> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves project into database.
     *
     * @param o is a {@code Project}, that represents Project to be saved.
     */
    public void saveEntity(Project o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates Project in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code Project}, that represents Project to be updated.
     */
    public void updateEntity(Project o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes Project with current fields values.
     *
     * @param o is a {@code Project}, that represents entity to be deleted.
     */
    public void deleteEntity(Project o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all Projects from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement, except {@code description}.
     *
     * @param o is an {@code Project}, that represents table to be retrieved.
     *          It can be any Project object, thus only class name is used.
     *
     * @return {@code List<Project>}, that represents array of all Projects.
     */
    public List<Project> getAll(Project o) {
        StringBuilder hql_query;
        boolean multiple; // determines if there is already 'where'

        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(Project.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as p where p.name = \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getCreated() != null) {
                if (!multiple) {
                    hql_query.append(" as p where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" p.created = \'").append(o.getCreated()).append("\'");
            }

            if (o.getUpdated() != null) {
                if (!multiple) {
                    hql_query.append(" as p where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" p.updated = \'").append(o.getUpdated()).append("\'");
            }

            if (o.getUser() != null && o.getUser().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as p where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" p.user.id = \'").append(o.getUser().getId()).append("\'");
            }

            return (List<Project>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns Project with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code Project}, that represents Project with 'id'.
     *
     * @return {@code Project}, that represents retrieved Project.
     */
    public Project getById(Project o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(Project.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as p where p.id = \'").append(o.getId()).append("\'");

            if (o.getUser() != null && o.getUser().getId() != 0) {
                hql_query.append(" and p.user.id = \'").append(o.getUser().getId()).append("\'");
            }

            return (Project) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new Project();
        }
    }
}
