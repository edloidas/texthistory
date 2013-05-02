package org.edloidas.dao.common;

import org.apache.log4j.Logger;
import org.edloidas.dao.CommonDao;
import org.edloidas.entity.common.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for User entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * <p/>
 * Warning: class not transactional. Use {@code UserService} instead.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.common.User
 */
@Repository
public class UserDao implements CommonDao<User> {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(UserDao.class);

    /**
     * Session Factory bean for manage sessions
     * See dispatcher-servlet.xml file for more details.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method saves User into database.
     *
     * @param o is a {@code User}, that represents User to be saved.
     */
    public void saveEntity(User o) {
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method updates User in database.
     * Basically, the comparison is by 'id'.
     *
     * @param o is a {@code User}, that represents User to be updated.
     */
    public void updateEntity(User o) {
        try {
            sessionFactory.getCurrentSession().update(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method deletes User with current fields values.
     *
     * @param o is a {@code User}, that represents User to be deleted.
     */
    public void deleteEntity(User o) {
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Method return all Users from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code User}, that represents table to be retrieved.
     *          It can be any User object, thus only class name is used.
     *
     * @return {@code List<User>}, that represents array of all Users.
     */
    public List<User> getAll(User o) {
        StringBuilder hql_query;
        boolean multiple; // determines if there is already 'where'

        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(User.class.getName());
            if (o.getLogin() != null) {
                hql_query.append(" as u where u.login = \'").append(o.getLogin()).append("\'");
                multiple = true;
            }

            if (o.getPassword() != null) {
                if (!multiple) {
                    hql_query.append(" as u where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" u.password = \'").append(o.getPassword()).append("\'");
            }

            if (o.getName() != null) {
                if (!multiple) {
                    hql_query.append(" as u where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" u.name = \'").append(o.getName()).append("\'");
            }

            return (List<User>) sessionFactory.getCurrentSession().createQuery(hql_query.toString()).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method return all User from database, that match some value or expression.
     * If {@code o} has other parameters, they are also will be included into select statement.
     * <b>Warning:</b> result is limited by some records. It should be used to improve query speed.
     *
     * @param o     is an {@code User} generic type, that represents entity to be compared.
     * @param limit value, that limits query records search.
     *
     * @return {@code List<User>} of  generic type, that represents array of objects,
     *         selected by some value.
     */
    @Override
    public List<User> getAll(User o, int limit) {
        StringBuilder hql_query;
        boolean multiple; // determines if there is already 'where'

        try {
            hql_query = new StringBuilder();
            multiple = false;

            hql_query.append("from ").append(User.class.getName());
            if (o.getLogin() != null) {
                hql_query.append(" as u where u.login = \'").append(o.getLogin()).append("\'");
                multiple = true;
            }

            if (o.getPassword() != null) {
                if (!multiple) {
                    hql_query.append(" as u where");
                    multiple = true;
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" u.password = \'").append(o.getPassword()).append("\'");
            }

            if (o.getName() != null) {
                if (!multiple) {
                    hql_query.append(" as u where");
                } else {
                    hql_query.append(" and");
                }
                hql_query.append(" u.name = \'").append(o.getName()).append("\'");
            }

            return (List<User>) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).setMaxResults(limit).list();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Method returns User with the same 'id'.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code User}, that represents User with 'id'.
     *
     * @return {@code User}, that represents retrieved User.
     */
    public User getById(User o) {
        StringBuilder hql_query;
        try {
            hql_query = new StringBuilder();

            hql_query.append("from ").append(User.class.getName());
            // If id is empty, exception will be thrown, because id is required
            hql_query.append(" as u where u.id = \'").append(o.getId()).append("\'");

            return (User) sessionFactory.getCurrentSession()
                    .createQuery(hql_query.toString()).list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex);
            return new User();
        }
    }
}
