package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.text.words.content.Concordance;
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
    @RequestMapping(value = "/statistic", method = RequestMethod.POST)
    String content(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

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

            if (userSession.isUpdateText()) {
                userSession.setUpdateText(false);
                textService.doUpdate(sb.toString());
            }

            float water = (1 - (float) textService.getMeanCount()
                    / (float) textService.getWordsCount()) * 100;
            model.addAttribute("textWordsCount", textService.getWordsCount());
            model.addAttribute("textWordsLength", textService.getWordsLength());
            model.addAttribute("textMeanCount", textService.getMeanCount());
            model.addAttribute("textWater", water);

            return "content-statistic";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "not-authorized";
        }
    }

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/key/list", method = RequestMethod.POST)
    String contentKeyList(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }
            // TODO : if there is too many key words - suggest to group them into categories.

            model.addAttribute("keyWords", textService.getKeyWords());
            model.addAttribute("count", textService.getKeyWords().size());

            return "content-key-list";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "not-authorized";
        }
    }

    /**
     * Handles and retrieves the AJAX request for viewing meaningful words.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
    @RequestMapping(value = "/key/view/{keyId}", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String contentMeaningfulView(@PathVariable("keyId") String keyId,
                                        Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }

            int id = Integer.parseInt(keyId);
            List<Concordance> cons = textService.getConcordance(id);
            int count = cons.size();
            String word = "";

            if (id < textService.getMeanWords().size()) {
                word = textService.getMeanWords().get(id).getName();
            }

            model.addAttribute("cons", cons);
            model.addAttribute("count", count);
            model.addAttribute("word", word);
            model.addAttribute("id", id);

            return "content-key-view";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "not-authorized";
        }
    }

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/graph", method = RequestMethod.POST)
    String contentMeaningfulGraph(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new Exception("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());

            if (userSession.getProject().getId() == -1) {
                return "no-project";
            }

            model.addAttribute("meanWords", textService.getMeanWords());
            model.addAttribute("count", textService.getMeanWords().size());

            return "content-graph";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "not-authorized";
        }
    }

    /**
     * Handles access to content section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
//    @RequestMapping(value = "/key/list", method = RequestMethod.GET)
//    String contentKeyList(Model model) {
//        try {
//            if (!userSession.isLogged()) {
//                throw new Exception("Access denied. User not authorized.");
//            }
//
//            model.addAttribute("sessionUser", userSession.getUser().getName());
//            model.addAttribute("sessionProject", userSession.getProject().getName());
//
//            if (userSession.getProject().getId() == -1) {
//                return "no-project";
//            }
//
//            model.addAttribute("keyWords", textService.getKeyWords());
//            model.addAttribute("count", textService.getKeyWords().size());
//
//            return "content-key-list";
//        } catch (Exception ex) {
//            LOGGER.error("Server error.", ex);
//            return "not-authorized";
//        }
//    }

    /**
     * Handles and retrieves the AJAX request for adding new key word.
     *
     * @return {@code String}, that represents text response of operation status.
     */
//    @RequestMapping(value = "/key/add", method = RequestMethod.POST,
//            produces = "application/json; charset=utf-8")
//    public
//    @ResponseBody
//    String contentKeyAdd(@RequestParam(value = "name", required = true) String name) {
//        try {
//            if (!userSession.isLogged())
//                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"You have no rights to do this.\"}";
//
//            JsonData json;
//            int id = 0;
//            if (textService.getKeyWords().size() > 0) {
//                id = textService.getKeyWords().get(textService.getKeyWords().size() - 1).getId() + 1;
//            }
//            textService.getKeyWords().add(new KeyWord(id, name));
//
//            json = new JsonData(0, "Category added.", name);
//
//            return json.toString();
//        } catch (Exception ex) {
//            LOGGER.info(ex.getMessage());
//            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
//        }
//    }

    /**
     * Handles and retrieves the AJAX request for adding new key word.
     *
     * @return {@code String}, that represents text response of operation status.
     */
//    @RequestMapping(value = "/key/delete", method = RequestMethod.POST,
//            produces = "application/json; charset=utf-8")
//    public
//    @ResponseBody
//    String contentKeyDelete(@RequestParam(value = "id", required = true) String keyId) {
//        try {
//            if (!userSession.isLogged())
//                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"You have no rights to do this.\"}";
//
//            JsonData json;
//            int id = Integer.parseInt(keyId);
//            json = new JsonData(0, "Category not found.", keyId);
//
//            for (int i = 0; i < textService.getKeyWords().size(); i++) {
//                if (textService.getKeyWords().get(i).getId() == id) {
//                    textService.getKeyWords().remove(i);
//                    json = new JsonData(0, "Category deleted.", keyId);
//                    break;
//                }
//            }
//
//            return json.toString();
//        } catch (Exception ex) {
//            LOGGER.info(ex.getMessage());
//            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
//        }
//    }

    /**
     * Handles and retrieves the AJAX request for viewing key words.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    /* Using mapping produces = "application/json; charset=utf-8" is highly recommended.
    *  In other case you will have bad Response with ISO character encoding. */
//    @RequestMapping(value = "/key/view/{keyId}", method = RequestMethod.GET,
//            produces = "application/json; charset=utf-8")
//    public String contentKeyView(@PathVariable("keyId") String keyId,
//                                        Model model) {
//        try {
//            if (!userSession.isLogged()) {
//                throw new Exception("Access denied. User not authorized.");
//            }
//
//            model.addAttribute("sessionUser", userSession.getUser().getName());
//            model.addAttribute("sessionProject", userSession.getProject().getName());
//
//            if (userSession.getProject().getId() == -1) {
//                return "no-project";
//            }
//
//            int id = Integer.parseInt(keyId);
//            for (KeyWord word : textService.getKeyWords()) {
//                if (word.getId() == id) {
//                    model.addAttribute("id", id);
//                    model.addAttribute("word", word.getName());
//
//                    model.addAttribute("meanWords", textService.getMeanWords());
//                    model.addAttribute("meanCount", textService.getMeanWords().size());
//
//                    model.addAttribute("keyMeanWords", word.getMeanWords());
//                    model.addAttribute("keyMeanCount", word.getMeanWords().size());
//
//                    List<SenseBlock> senseBlocks = textService.getSenseBlocks();
//                    model.addAttribute("senseBlocks", senseBlocks);
//                    model.addAttribute("senseCount", senseBlocks.size());
//
//                    return "content-key-view";
//                }
//            }
//            LOGGER.error("KeyWord not found.");
//            return "content-key-list";
//        } catch (Exception ex) {
//            LOGGER.error("Server error.", ex);
//            return "not-authorized";
//        }
//    }

    /**
     * Handles and retrieves the AJAX request for adding new key word.
     *
     * @return {@code String}, that represents text response of operation status.
     */
//    @RequestMapping(value = "/key/view", method = RequestMethod.POST,
//            produces = "application/json; charset=utf-8")
//    public
//    @ResponseBody
//    String contentKeyMeanUpdate(@RequestParam(value = "keyId", required = true) String keyId,
//                                @RequestParam(value = "meanId", required = true) String meanId) {
//        try {
//            if (!userSession.isLogged())
//                return "{\"code\":2,\"msg\":\"Access denied.\",\"data\":\"You have no rights to do this.\"}";
//
//            JsonData json;
//            int keyIdVal = Integer.parseInt(keyId);
//            int meanIdVal = Integer.parseInt(meanId);
//
//            for (KeyWord key : textService.getKeyWords()) {
//                if (key.getId() == keyIdVal) {
//                    for (int i = 0; i < key.getMeanWords().size(); i++) {
//                        if (key.getMeanWords().get(i).getId() == meanIdVal) {
//                            key.getMeanWords().remove(i);
//                            json = new JsonData(3, "Category inactivated.", "inactivated");
//                            return json.toString();
//                        }
//                    }
//                    key.getMeanWords().add(textService.getMeanWords().get(meanIdVal));
//                    json = new JsonData(3, "Category activated.", "activated");
//                    return json.toString();
//                }
//            }
//
//            //json = new JsonData(0, "Category added.", status);
//            json = new JsonData(4, "Category not found.", ErrorCode.CODE_4);
//
//            return json.toString();
//        } catch (Exception ex) {
//            LOGGER.info(ex.getMessage());
//            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
//        }
//    }

//    private String navaigateToProjects(Model model) {
//        List<Project> projects;
//        int count;
//
//        projects = projectService.getAll(new Project(userSession.getUser()));
//        count = projects.size();
//        model.addAttribute("projects", projects);
//        model.addAttribute("count", count);
//
//        return "project-list";
//    }
}
