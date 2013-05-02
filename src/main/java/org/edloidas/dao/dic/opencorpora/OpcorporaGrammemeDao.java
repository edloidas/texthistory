package org.edloidas.dao.dic.opencorpora;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.dic.opencorpora.OpcorporaGrammeme;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for OpcorporaGrammeme entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code OpcorporaGrammemeService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.dic.opencorpora.OpcorporaGrammeme
 */
@Repository
public class OpcorporaGrammemeDao implements CommonDao<OpcorporaGrammeme> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(OpcorporaGrammemeDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves OpcorporaGrammeme into database.
     *
     * @param o is a {@code OpcorporaGrammeme}, that represents User to be saved.
     */
    public void saveEntity(OpcorporaGrammeme o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates OpcorporaGrammeme in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme to be updated.
     */
    public void updateEntity(OpcorporaGrammeme o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes OpcorporaGrammeme with current fields values.
     *
     * @param o is a {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme to be deleted.
     */
    public void deleteEntity(OpcorporaGrammeme o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all OpcorporaGrammeme from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o an {@code OpcorporaGrammeme}, that represents table to be retrieved.
     *          It can be any OpcorporaGrammeme object.
     *
     * @return {@code List<OpcorporaGrammeme>}, that represents array of all OpcorporaGrammeme.
     */
    public List<OpcorporaGrammeme> getAll(OpcorporaGrammeme o) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(OpcorporaGrammeme.class.getName());
            if (o.getName() != null) {
                //hql_query.append(" as a where a.name like \'").append(o.getName()).append("%\'");
                hql_query.append(" as g where g.name like \'").append(o.getName()).append("\'");
                multiple = true;
            }

            if (o.getAlias() != null) {
                if (!multiple) {
                    hql_query.append(" as g where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.alias like \'").append(o.getAlias()).append("\'");
            }

            if (o.getDescription() != null) {
                if (!multiple) {
                    hql_query.append(" as g where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.description like \'").append(o.getDescription()).append("\'");
            }

            if (o.getParent() != null && o.getParent().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as g where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.opcorpora_grammeme.id = \'").append(o.getParent().getId()).append("\'");
            }

            return (List<OpcorporaGrammeme>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all OpcorporaGrammeme from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * Method uses 'word%' matcher to find record with name, if present.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code OpcorporaGrammeme} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<OpcorporaGrammeme>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<OpcorporaGrammeme> getAll(OpcorporaGrammeme o, int limit) {
        StringBuilder hql_query;
        boolean multiple;
        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(OpcorporaGrammeme.class.getName());
            if (o.getName() != null) {
                hql_query.append(" as g where g.name like \'").append(o.getName()).append("%\'");
                multiple = true;
            }

            if (o.getAlias() != null) {
                if (!multiple) {
                    hql_query.append(" as g where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.alias like \'").append(o.getAlias()).append("\'");
            }

            if (o.getDescription() != null) {
                if (!multiple) {
                    hql_query.append(" as g where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.description like \'").append(o.getDescription()).append("\'");
            }

            if (o.getParent() != null && o.getParent().getId() != 0) {
                if (!multiple) {
                    hql_query.append(" as g where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" g.opcorpora_grammeme.id = \'").append(o.getParent().getId()).append("\'");
            }

            return (List<OpcorporaGrammeme>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns OpcorporaGrammeme with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code OpcorporaGrammeme}, that represents OpcorporaGrammeme with 'id'.
     *
     * @return {@code OpcorporaGrammeme}, that represents retrieved OpcorporaGrammeme.
     */
    public OpcorporaGrammeme getById(OpcorporaGrammeme o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(OpcorporaGrammeme.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as g where g.id = \'").append(o.getId()).append("\'");

            return (OpcorporaGrammeme) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new OpcorporaGrammeme();
        }
    }
}
