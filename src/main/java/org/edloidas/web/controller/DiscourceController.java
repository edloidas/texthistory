package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.text.words.discource.DiscourceGroup;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.edloidas.web.service.TextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Content Controller, that handles requests for content analysis.
 *
 * @author Никита
 */
@Controller
@RequestMapping(value = "/discource")
@SessionAttributes({"userSession", "textService"})
public class DiscourceController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(DiscourceController.class);

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
     * Text session variables holder.
     *
     * @see org.edloidas.web.service.TextService
     */
    @Resource(name = "textService")
    private TextService textService;

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String discourse(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            if (userSession.getProject().getId() == -1) {
                return navaigateToProjects(model);
            }

            if (userSession.isUpdateText()) {
                List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
                StringBuilder sb = new StringBuilder();

                for (Source src : sources) {
                    if (src.getStatus())
                        sb.append(src.getText()).append("\n");
                }

                userSession.setUpdateText(false);
                textService.doUpdate(sb.toString());
            }

            List<DiscourceGroup> groups = textService.getDiscourceGroups();
            model.addAttribute("discGroups", groups);
            model.addAttribute("count", groups.size());

            return "discource";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "index";
        }
    }

    /**
     * Handles and retrieves the AJAX request for viewing discource words.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/view/{discId}", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String discourseView(@PathVariable("discId") String discId,
                                Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            if (userSession.getProject().getId() == -1) {
                return navaigateToProjects(model);
            }

            int id = Integer.parseInt(discId);
            DiscourceGroup group = textService.getDiscourceGroups().get(id);

            model.addAttribute("id", id);
            model.addAttribute("group", group);
            model.addAttribute("cons", group.getConcordance());
            model.addAttribute("count", group.getConcordance().size());

            return "discource-view";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "index";
        }
    }

    private String navaigateToProjects(Model model) {
        List<Project> projects;
        int count;

        projects = projectService.getAll(new Project(userSession.getUser()));
        count = projects.size();
        model.addAttribute("projects", projects);
        model.addAttribute("count", count);

        return "project-list";
    }
}
