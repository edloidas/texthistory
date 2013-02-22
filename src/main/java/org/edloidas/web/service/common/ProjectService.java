package org.edloidas.web.service.common;

import org.edloidas.dao.common.ProjectDao;
import org.edloidas.entity.common.Project;
import org.edloidas.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring service for project database entity.
 * Provides 'CRUD' (create, read, update, delete) functions.
 * Exceptions are generated automatically, due to transactional annotation.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.dao.common.ProjectDao
 * @see org.edloidas.entity.common.Project
 */
@Service("projectService")
public class ProjectService implements EntityService<Project> {

    /** Project DAO, that implements all business logic. */
    @Autowired
    private ProjectDao projectDao;

    /**
     * Method adds new project.
     *
     * @param o is a {@code Project}, that represents project to be added.
     */
    @Transactional
    public void addEntity(Project o) {
        projectDao.saveEntity(o);
    }

    /**
     * Method updates existed project.
     *
     * @param o is a {@code Project}, that represents project to be updated.
     */
    @Transactional
    public void updateEntity(Project o) {
        projectDao.updateEntity(o);
    }

    /**
     * Method deletes existed project.
     *
     * @param o is a {@code Project} generic type, that represents project to be deleted.
     */
    @Transactional
    public void deleteEntity(Project o) {
        projectDao.deleteEntity(o);
    }

    /**
     * Method gets all projects from database.
     * If {@code o} has other parameters, they are also will be included into select statement.
     *
     * @param o is an {@code Project}, that represents Project to be retrieved.
     *
     * @return {@code List<Project>}, that represents array of projects.
     */
    @Transactional
    public List<Project> getAll(Project o) {
        return projectDao.getAll(o);
    }

    /**
     * Method gets project with the same 'id'.
     * If {@code o} has {@code user.id} parameter, it will also be included into select statement.
     *
     * @param o is an {@code int}, that represents projects's 'id'.
     *
     * @return {@code Project}, that represents retrieved project.
     */
    @Transactional
    public Project getById(Project o) {
        return projectDao.getById(o);
    }
}
