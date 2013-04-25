package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.text.words.content.Concordance;
import org.edloidas.web.exception.NotAuthorizedException;
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
 * @author edloidas
 */
@Controller
@RequestMapping(value = "/content")
@SessionAttributes({"userSession", "textService"})
public class ContentController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(ContentController.class);

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
    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String content(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new NotAuthorizedException("Access denied. User not authorized.");
            }

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }

            List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
            StringBuilder sb = new StringBuilder();

            for (Source src : sources) {
                if (src.getStatus())
                    sb.append(src.getText()).append("\n");
            }

            model.addAttribute("text", sb.toString());
            if (!userSession.isTextUpdated()) {
                textService.doUpdate(sb.toString());
                userSession.setTextUpdated(true);
            }

            float water = (100.0f - textService.getKeyPercentage());
            model.addAttribute("textWordsCount", textService.getWordsCount());
            model.addAttribute("textWordsLength", textService.getWordsLength());
            model.addAttribute("textKeyCount", textService.getKeyCount());
            model.addAttribute("textWater", water);

            return "content-statistic";
        } catch (NotAuthorizedException ex) {
            LOGGER.warn(ex.getMessage());
            return "not-authorized";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "server-error";
        }
    }

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/key/list", method = RequestMethod.GET)
    public String contentKeyList(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new NotAuthorizedException("Access denied. User not authorized.");
            }

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }
            // TODO : if there is too many key words - suggest to group them into categories.

            if (!userSession.isTextUpdated()) {
                List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
                StringBuilder sb = new StringBuilder();

                for (Source src : sources) {
                    if (src.getStatus())
                        sb.append(src.getText()).append("\n");
                }

                textService.doUpdate(sb.toString());
                userSession.setTextUpdated(true);
            }

            model.addAttribute("categories", textService.getCategories());
            model.addAttribute("count", textService.getCategories().size());

            return "content-key-list";
        } catch (NotAuthorizedException ex) {
            LOGGER.warn(ex.getMessage());
            return "not-authorized";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "server-error";
        }
    }

    /**
     * Handles and retrieves the AJAX request for viewing meaningful words.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/key/view/{keyId}", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String contentMeaningfulView(@PathVariable("keyId") String keyId,
                                        Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new NotAuthorizedException("Access denied. User not authorized.");
            }

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }

            int id = Integer.parseInt(keyId);

            if (!userSession.isTextUpdated()) {
                List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
                StringBuilder sb = new StringBuilder();

                for (Source src : sources) {
                    if (src.getStatus())
                        sb.append(src.getText()).append("\n");
                }

                textService.doUpdate(sb.toString());
                userSession.setTextUpdated(true);
            }

            //model.addAttribute("categories", textService.getCategories()); // For future use: merging categories
            model.addAttribute("category", textService.getCategories().get(id));
            model.addAttribute("keys", textService.getCategories().get(id).getKeyWords());

            return "content-key-view";
        } catch (NotAuthorizedException ex) {
            LOGGER.warn(ex.getMessage());
            return "not-authorized";
        } catch (IndexOutOfBoundsException ex) {
            return "no-index";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "server-error";
        }
    }

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String contentMeaningfulGraph(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new NotAuthorizedException("Access denied. User not authorized.");
            }

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }

            List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
            StringBuilder sb = new StringBuilder();

            for (Source src : sources) {
                if (src.getStatus())
                    sb.append(src.getText()).append("\n");
            }

            model.addAttribute("text", sb.toString());

            if (!userSession.isTextUpdated()) {
                textService.doUpdate(sb.toString());
                userSession.setTextUpdated(true);
            }

            model.addAttribute("categories", textService.getCategories());
            model.addAttribute("count", textService.getCategories().size());

            return "content-graph";
        } catch (NotAuthorizedException ex) {
            LOGGER.warn(ex.getMessage());
            return "not-authorized";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "server-error";
        }
    }
}
