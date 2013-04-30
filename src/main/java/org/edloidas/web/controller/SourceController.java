package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.web.json.JsonData;
import org.edloidas.web.json.Message;
import org.edloidas.web.json.SimpleMessage;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Source Controller, that handles requests for sources adding, removing and including.
 *
 * @author edloidas
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
     * Handles and retrieves the AJAX request for file uploading to specific project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/upload/{projectId}", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String sourceUpload(@PathVariable("projectId") String id,
                             @RequestParam(value = "file", required = true) CommonsMultipartFile file) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                // Check for rights
                Project project = projectService.getById(new Project(Integer.parseInt(id)));
                if (project.getUser().getId() != userSession.getUser().getId()) { // user not owned this project
                    msg = new SimpleMessage(Message.CODE_NO_RIGHTS);
                } else {                                                          // user has rights
                    StringBuilder sb = new StringBuilder();
                    String text;

                    while ((text = in.readLine()) != null) {
                        sb.append(text).append("\n");
                    }
                    text = sb.toString();

                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    md5.update(text.getBytes(), 0, text.length());
                    BigInteger md5Number = new BigInteger(1, md5.digest());

                    List<Source> sources = sourceService.getAll(new Source(md5Number.toString(16)));
                    if (sources.size() != 0) {
                        msg = new SimpleMessage(Message.CODE_ALREADY_EXISTS);
                    } else {
                        Source src = new Source(file.getOriginalFilename(), text, md5Number.toString(16), project);
                        sourceService.addEntity(src);
                        userSession.setTextUpdated(false);

                        msg = new SimpleMessage();
                    }
                }
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for removing sources from project.
     * Sources should still remain in the database.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String sourceRemove(@RequestParam(value = "id", required = true) String id) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Source src = sourceService.getById(new Source(Integer.parseInt(id)));
                Project project = projectService.getById(src.getProject());

                if (project.getUser().getId() != userSession.getUser().getId()) { // user not owned this project
                    msg = new SimpleMessage(Message.CODE_NO_RIGHTS);
                } else {
                    if (project.getId() == userSession.getProject().getId()       // deleting from opened project
                            && src.getStatus()) {                                 // and source was active
                        userSession.setTextUpdated(false);
                    }

                    sourceService.deleteEntity(new Source(Integer.parseInt(id)));

                    msg = new SimpleMessage();
                }
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
    @RequestMapping(value = "/status", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String sourceStatus(@RequestParam(value = "id", required = true) String id) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SimpleMessage(Message.CODE_NOT_LOGGED);
            } else {
                Source src = sourceService.getById(new Source(Integer.parseInt(id)));
                Project project = projectService.getById(src.getProject());

                if (project.getUser().getId() != userSession.getUser().getId()) { // user not owned this project
                    msg = new SimpleMessage(Message.CODE_NO_RIGHTS);
                } else {
                    src.setStatus(!src.getStatus());

                    sourceService.updateEntity(src);

                    if (project.getId() == userSession.getProject().getId()) {
                        userSession.setTextUpdated(false);
                    }

                    msg = new SimpleMessage();
                }
            }

            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }
}
