package org.edloidas.dao.dic.discource;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.discource.DiscourceCategory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for DiscourceCategory entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code DiscourceCategoryService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.discource.DiscourceCategory
 */
@Repository
public class DiscourceCategoryDao implements CommonDao<DiscourceCategory> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(DiscourceCategoryDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves DiscourceCategory into database.
     *
     * @param o is a {@code DiscourceCategory}, that represents DiscourceCategory to be saved.
     */
    public void saveEntity(DiscourceCategory o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates DiscourceCategory in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code DiscourceCategory}, that represents DiscourceCategory to be updated.
     */
    public void updateEntity(DiscourceCategory o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes DiscourceCategory with current fields values.
     *
     * @param o is a {@code DiscourceCategory}, that represents DiscourceCategory to be deleted.
     */
    public void deleteEntity(DiscourceCategory o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all DiscourceCategory from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code DiscourceCategory}, that represents table to be retrieved.
     *          It can be any DiscourceCategory object.
     *
     * @return {@code List<DiscourceCategory>}, that represents array of all DiscourceCategory.
     */
    public List<DiscourceCategory> getAll(DiscourceCategory o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(DiscourceCategory.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as d where d.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getExample() != null) {
                if (!multiple) {
                    hql_query.append(" as d where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.example like \'").append(o.getExample()).append("\'");
            }

            if (o.getEffects() != null) {
                if (!multiple) {
                    hql_query.append(" as d where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.effects like \'").append(o.getEffects()).append("\'");
            }

            if (o.getParent() != null && o.getParent().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as d where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.discource_category.id = \'").append(o.getParent().getId()).append("\'");
            }

            return (List<DiscourceCategory>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all DiscourceCategory from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code DiscourceCategory} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<DiscourceCategory>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<DiscourceCategory> getAll(DiscourceCategory o, int limit) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(DiscourceCategory.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as d where d.name like \'").append(o.getName()).append("%\'");
                multiple = true;
            }

            if (o.getExample() != null) {
                if (!multiple) {
                    hql_query.append(" as d where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.example like \'").append(o.getExample()).append("\'");
            }

            if (o.getEffects() != null) {
                if (!multiple) {
                    hql_query.append(" as d where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.effects like \'").append(o.getEffects()).append("\'");
            }

            if (o.getParent() != null && o.getParent().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as d where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" d.discource_category.id = \'").append(o.getParent().getId()).append("\'");
            }

            return (List<DiscourceCategory>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns DiscourceCategory with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code DiscourceCategory}, that represents DiscourceCategory with 'id'.
     *
     * @return {@code DiscourceCategory}, that represents retrieved DiscourceCategory.
     */
    public DiscourceCategory getById(DiscourceCategory o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(DiscourceCategory.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as d where d.id = \'").append(o.getId()).append("\'");

            return (DiscourceCategory) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new DiscourceCategory();
        }
    }
}
