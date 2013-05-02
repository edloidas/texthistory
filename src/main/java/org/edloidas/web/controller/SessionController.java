package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;
import org.edloidas.web.exception.NotAuthorizedException;
import org.edloidas.web.json.Message;
import org.edloidas.web.json.SessionMessage;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.edloidas.web.service.TextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller, that give access to session data, like session hash, key words,
 * text, etc.
 *
 * @author edloidas
 */
@Controller
@RequestMapping(value = "/session")
@SessionAttributes({"userSession", "textService"})
public class SessionController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(SessionController.class);

    /**
     * Session variables holder.
     *
     * @see org.edloidas.web.service.SessionService
     */
    @Resource(name = "sessionService")
    private SessionService userSession;

    /**
     * Source service for CRUD operations.
     *
     * @see org.edloidas.web.service.common.SourceService
     */
    @Resource(name = "sourceService")
    private EntityService<Source> sourceService;

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
    @RequestMapping(value = "/hash", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String getHash(Model model) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                msg = new SessionMessage(Message.CODE_NOT_LOGGED);
            } else { // empty hash is equals to "no project selected"
                msg = new SessionMessage(Message.CODE_SUCCESS, userSession.getHash());
            }
            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }

    /**
     * Handles access to result data.
     *
     * @return {@code String} of JSON data interpretation.
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String getData(Model model) {
        try {
            Message msg;
            if (!userSession.isLogged()) {
                return (new SessionMessage(Message.CODE_NOT_LOGGED)).toString();
            } else { // empty hash is equals to "no project selected"
                if (userSession.getProject().getId() == -1) {
                    msg = new SessionMessage(Message.CODE_NO_PROJECT);
                } else {
                    if (!userSession.isTextUpdated()) { // Text has been changed in project, but not yet updated.
                        List<Source> sources = sourceService.getAll(new Source(userSession.getProject()));
                        StringBuilder sb = new StringBuilder();

                        for (Source src : sources) {
                            if (src.getStatus())
                                sb.append(src.getText()).append("\n");
                        }

                        textService.doUpdate(sb.toString());
                        userSession.setTextUpdated(true); // Text is up to date.
                    }

                    msg = new SessionMessage(userSession.getHash(), textService.getData());
                }
            }
            return msg.toString();
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "{\"code\":" + Message.CODE_SERVER_ERROR + "}";
        }
    }
}
