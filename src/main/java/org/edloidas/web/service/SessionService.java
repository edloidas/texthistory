package org.edloidas.web.service;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * JavaBean
 * Default user session, that stores username and operating project.
 *
 * @author edloidas@gmail.com
 */
@Service("sessionService")
@Transactional
public class SessionService {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(SessionService.class);

    /** Current user */
    private User user;
    /** Current project */
    private Project project;
    /** Log status indicator */
    private boolean logged;
    /** Log status indicator */
    private boolean textUpdated;

    /** Basic constructor. Set all field to "none" string. */
    public SessionService() {
        user = new User();
        project = new Project();
        project.setId(-1);
        project.setName("не выбран");
        logged = false;
        textUpdated = false;
    }

    /**
     * Method gets current user.
     *
     * @return {@code User}, that represents current logged user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Method initializes new user.
     *
     * @param user is a {@code User}, that represents new logged user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method gets current project.
     *
     * @return {@code Project}, that represents currently opened project.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Method initializes new project.
     *
     * @param project is a {@code Project}, that represents currently opened project.
     */
    public boolean setProject(Project project) {
        if (project.getUser().getId() != this.user.getId())
            return false;
        // else
        this.project = project;
        return true;
    }

    /**
     * Method returns current logged status.
     *
     * @return {@code true}, if user is logged in, otherwise return {@code false}.
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * Method set logged status.
     *
     * @param logged is a {@code boolean}, that represents logged status.
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isTextUpdated() {
        return textUpdated;
    }

    public void setTextUpdated(boolean textUpdated) {
        this.textUpdated = textUpdated;
    }

    /**
     * Authorize user. Check login as string and password as md5 hash.
     *
     * @param auth is a {@code User}, that represents user to be authorized.
     * @param db   is a {@code User}, that already exists in database.
     *
     * @return {@code true}, if user is authorized, otherwise return {@code false}.
     */
    public boolean setLogged(User auth, User db) {
        try {
            BigInteger pass1, pass2;
            MessageDigest pass1md = MessageDigest.getInstance("MD5");
            MessageDigest pass2md = MessageDigest.getInstance("MD5");
            if (auth.getLogin().equals(db.getLogin())) {
                pass1md.update(auth.getPassword().getBytes(), 0, auth.getPassword().length());
                pass2md.update(db.getPassword().getBytes(), 0, db.getPassword().length());
                pass1 = new BigInteger(1, pass1md.digest());
                pass2 = new BigInteger(1, pass2md.digest());
                LOGGER.info("Compare " + pass1.toString(16) + " with " + pass2.toString(16));
                if (pass1.compareTo(pass2) == 0) {
                    this.user = db;
                    this.logged = true;
                    this.textUpdated = false;
                    closeProject();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }
    }

    /**
     * Method closes project.
     *
     * @return {@code true}, if project is closed, otherwise return {@code false}.
     */
    public boolean closeProject() {
        try {
            this.project = new Project();
            this.project.setId(-1);
            this.project.setName("не выбран");
            this.textUpdated = false;
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }
        return true;
    }
}
