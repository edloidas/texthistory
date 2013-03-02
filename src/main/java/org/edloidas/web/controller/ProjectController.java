package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.web.json.JsonData;
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
 * @author Никита
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
    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String projectList(Model model) {
        try {
            if (!userSession.isLogged()) {
                return "index";
            }

            List<Project> projects;
            int count;

            projects = projectService.getAll(new Project(userSession.getUser()));
            count = projects.size();
            model.addAttribute("projects", projects);
            model.addAttribute("count", count);

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            return "project-list";
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            /* TODO: Replace with error pages */
            return "home";
        }
    }

    /**
     * Handles and retrieves the non-AJAX project add page.
     *
     * @return {@code String}, that represents view.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String projectNew(Model model) {
        try {
            if (!userSession.isLogged()) {
                return "index";
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            return "project-add";
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            /* TODO: Replace with error pages */
            return "home";
        }
    }

    /**
     * Handles and retrieves the AJAX request for adding new project.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/new/save", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectAdd(@RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "desc", required = true) String desc) {
        try {
            if (!userSession.isLogged()) {
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";
            }

            JsonData json;

            Project project = new Project(name, desc, userSession.getUser());

            projectService.addEntity(project);

            json = new JsonData(0, "Project added.", project.getName());

            return json.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
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
            if (!userSession.isLogged()) {
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";
            }

            JsonData json;

            Project project = new Project();
            project.setId(Integer.parseInt(id));
            /* TODO: Check, if user has permission to delete this project. And replace messages with codes. */

            List<Source> src1 = sourceService.getAll(new Source(project));
            List<Source> src2 = new ArrayList<>();
            for (Source s : src1) {
                src2.add(new Source(s.getId()));
            }
            project.setSources(src2);

            projectService.deleteEntity(project);

            if (project.getId() == userSession.getProject().getId()) {
                userSession.closeProject();
                json = new JsonData(3, "Project closed and deleted.", "none");
            } else {
                json = new JsonData(0, "Project deleted.", "none");
            }
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
    @RequestMapping(value = "/open", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectOpen(@RequestParam(value = "id", required = true) String id) {
        try {
            if (!userSession.isLogged()) {
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";
            }

            JsonData json;
            Project project = projectService.getById(new Project(Integer.parseInt(id)));
            if (userSession.setProject(project)) {
                json = new JsonData(3, "Project with name [" + project.getName() + "] opened.", project.getName());
                userSession.setTextUpdated(false);
            } else {
                json = new JsonData(2, "Project can not be opened.", "Something goes wrong.");
            }
            return json.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
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
    @RequestMapping(value = "/view/{projectId}", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String projectView(@PathVariable("projectId") String projectId,
                              Model model) {
        try {
            if (!userSession.isLogged()) {
                return "index";
            }

            Project project;
            List<Source> sources;
            int count;
            int id = Integer.parseInt(projectId);

            project = projectService.getById(new Project(id));
            if (project.getUser().getId() != userSession.getUser().getId()) {
                // TODO: Add model message here.
                return "project-list";
            }

            sources = sourceService.getAll(new Source(new Project(id)));
            count = sources.size();

            model.addAttribute("project", project);
            model.addAttribute("sources", sources);
            model.addAttribute("count", count);

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            return "project-view";
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            /* TODO: Replace with error pages */
            return "project-list";
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
            if (!userSession.isLogged()) {
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";
            }

            JsonData json;
            Project project = projectService.getById(new Project(Integer.parseInt(id)));
            project.setName(name);
            project.setDescription(desc);
            project.update();
            projectService.updateEntity(project);

            if (userSession.getProject().getId() != Integer.parseInt(id)) {
                json = new JsonData(0, "Project with name [" + project.getName() + "] updated.", project.getName());
            } else { // Update if saved project is currently opened.
                userSession.setProject(project);
                json = new JsonData(3, "Project with name [" + project.getName() + "] updated.", project.getName());
            }
            return json.toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for file uploading.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/{projectId}/upload", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String projectFileUpload(@PathVariable("projectId") String id,
                             @RequestParam(value = "file", required = true) CommonsMultipartFile file) {
        try {
            if (!userSession.isLogged()) {
                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"User has no rights to do this.\"}";
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = in.readLine()) != null) {
                sb.append(text).append("\n");
            }
            text = sb.toString();

            // Get MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(text.getBytes(), 0, text.length());
            BigInteger md5Number = new BigInteger(1, md5.digest());

            List<Source> sources = sourceService.getAll(new Source(md5Number.toString(16)));
            if (sources.size() != 0) {
                return new JsonData(1, "File [" + file.getOriginalFilename() + "] not added. Reason: file with the same signature already exists.", "File with the same signature already exists.").toString();
            }
            Project project = projectService.getById(new Project(Integer.parseInt(id)));
            Source src = new Source(file.getOriginalFilename(), text, md5Number.toString(16), project);
            sourceService.addEntity(src);
            userSession.setTextUpdated(false);

            return new JsonData(3, "File [" + file.getOriginalFilename() + "] uploaded with total size of " + file.getSize() + " Bytes.", "Total size: " + file.getSize()).toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }
}
