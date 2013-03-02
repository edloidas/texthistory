package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.web.json.JsonData;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Source Controller, that handles requests for sources adding, removing and including.
 *
 * @author Никита
 */
@Controller
@RequestMapping(value = "/source")
@SessionAttributes({"userSession"})
public class SourceController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(SourceController.class);

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
            if (!userSession.isLogged())
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";

            JsonData json;

            Source src = sourceService.getById(new Source(Integer.parseInt(id)));
            Project prj = projectService.getById(src.getProject());

            if (userSession.getProject().getName().equals(prj.getName()) // deleting from opened project
                    && src.getStatus()) {                      // source was active
                userSession.setTextUpdated(false);
            }

            sourceService.deleteEntity(new Source(Integer.parseInt(id)));

            json = new JsonData(0, "Source deleted.", "none");

            return json.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for opening project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/status", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectStatus(@RequestParam(value = "id", required = true) String id) {
        try {
            if (!userSession.isLogged())
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";

            JsonData json;

            /* TODO: Check, if user has permission to delete this project. And replace messages with codes. */
            //sourceService.deleteEntity(new Source(Integer.parseInt(id)));
            Source src = sourceService.getById(new Source(Integer.parseInt(id)));
            src.setStatus(!src.getStatus());

            sourceService.updateEntity(src);

            // TODO: Check if updated source refer to opened project.
            userSession.setTextUpdated(false);

            json = new JsonData(3, "Source [" + src.getName() + "] status was changed.", src.getStatus().toString());

            return json.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }
}
