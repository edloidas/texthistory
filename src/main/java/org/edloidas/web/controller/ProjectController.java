package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.web.exception.NotAuthorizedException;
import org.edloidas.web.json.*;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Controller, that handles requests for project operations and source adding.
 *
 * @author edloidas
 */
@Controller
@RequestMapping(value = "/project")
@SessionAttributes({"userSession"})
public class ProjectController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class);

    /**
     * Project service for CRUD operations.
     *
     * @see org.edloidas.web.service.common.ProjectService
     */
    /* USE Interface here.
     * Otherwise SPRING will throw BeanNotOfRequiredTypeException.
     * Spring uses the interface type to make dependency injection!
     */
    @Resource(name = "projectService")
    private EntityService<Project> projectService;

    /**
     * Source service for CRUD operations.
     *
     * @see org.edloidas.web.service.common.SourceService
     */
    /* USE Interface here.
     * Otherwise SPRING will throw BeanNotOfRequiredTypeException.
     * Spring uses the interface type to make dependency injection!
     */
    @Resource(name = "sourceService")
    private EntityService<Source> sourceService;

    /**
     * Session variables holder.
     *
     * @see org.edloidas.web.service.SessionService
     */
    @Resource(name = "sessionService")
    private SessionService userSession;

    /**
     * Handles and retrieves the non-AJAX project list page.
     *
     * @return {@code String}, that represents view.
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectList() {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                msg = new ProjectListMessage(projectService.getAll(new Project(userSession.getUser())));
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for adding new project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectAdd(@RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "desc", required = true) String desc) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Project project = new Project(name, desc, userSession.getUser());
                projectService.addEntity(project);
                msg = new SimpleMessage();
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for deleting project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectDelete(@RequestParam(value = "id", required = true) String id) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Project project = new Project();
                project.setId(Integer.parseInt(id));

                /* TODO: Check, if user has permission to delete this project. Return CODE_NO_RIGHTS if no*/

                List<Source> src1 = sourceService.getAll(new Source(project));
                List<Source> src2 = new ArrayList<>();
                for (Source s : src1) {
                    src2.add(new Source(s.getId()));
                }
                project.setSources(src2);

                projectService.deleteEntity(project);

                if (project.getId() == userSession.getProject().getId()) {
                    userSession.closeProject();
                }
                msg = new SimpleMessage();
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for opening project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/open", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectOpen(@RequestParam(value = "id", required = true) String id) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Project project = projectService.getById(new Project(Integer.parseInt(id)));
                if (userSession.setProject(project)) {
                    userSession.setTextUpdated(false);
                    msg = new SimpleMessage();
                } else {
                    msg = new SimpleMessage(Message.CODE_SERVER_ERROR);
                }

            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for viewing project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding.
    *  TODO: Add view by name for mapping '/view/name/{projectName}+' */
    @RequestMapping(value = "/view/{projectId}", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String projectView(@PathVariable("projectId") String projectId) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Project project;
                List<Source> sources;
                int id = Integer.parseInt(projectId);

                project = projectService.getById(new Project(id));
                if (project.getUser().getId() != userSession.getUser().getId()) {
                    msg = new SimpleMessage(Message.CODE_NO_RIGHTS);
                } else {
                    sources = sourceService.getAll(new Source(new Project(id)));
                    msg = new ProjectMessage(project, sources);
                }
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for updating project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/update", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectUpdate(@RequestParam(value = "id", required = true) String id,
                         @RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "desc", required = true) String desc) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Project project = projectService.getById(new Project(Integer.parseInt(id)));
                project.setName(name);
                project.setDescription(desc);
                project.update();
                projectService.updateEntity(project);

                if (userSession.getProject().getId() == Integer.parseInt(id)) {
                    userSession.setProject(project);
                }

                msg = new SimpleMessage();
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }
}
