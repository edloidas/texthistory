package org.edloidas.web.controller;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.User;
import org.edloidas.web.exception.NotAuthorizedException;
import org.edloidas.web.json.JsonData;
import org.edloidas.web.service.EntityService;
import org.edloidas.web.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Home Controller, that handles requests for login, registration and home page.
 *
 * @author edloidas
 */
@Controller
@SessionAttributes({"userSession"})
public class HomeController {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(HomeController.class);

    /* USE Interface here.
     * Otherwise SPRING will throw BeanNotOfRequiredTypeException.
     * Spring uses the interface type to make dependency injection!
     */
    @Resource(name = "userService")
    private EntityService<User> userService;

    /**
     * Session variables holder.
     *
     * @see org.edloidas.web.service.SessionService
     */
    @Resource(name = "sessionService")
    private SessionService userSession;

    /**
     * Handles and retrieves the AJAX request for user login.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String login(@RequestParam(value = "login", required = true) String login,
                 @RequestParam(value = "password", required = true) String password) {
        try {
            List<User> users = userService.getAll(new User());
            User auth = new User();
            auth.setLogin(login);
            auth.setPassword(password);
            for (User user : users) {
                if (userSession.setLogged(auth, user)) {
                    return new JsonData(0, "User successfully logged.", "Logging succeeded").toString();
                } // fail is default
            }
            return new JsonData(2, "Incorrect login or password.", "Logging failed.").toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }

    /**
     * Handles and retrieves the AJAX request for user logout.
     *
     * @return {@code String}, that represents text response of operation status.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public
    @ResponseBody
    String logout() {
        try {
            userSession.setLogged(false);
            return new JsonData(0, "Logout accomplished successfully.", "Logout succeeded").toString();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return "{\"code\":1,\"msg\":\"Server error.\",\"data\":\"See server log.\"}";
        }
    }

    /**
     * Handles access to home section.
     *
     * @return {@code String}, that represents text response of operation success.
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String base(Model model) {
        try {
            if (!userSession.isLogged()) {
                throw new NotAuthorizedException("Access denied. User not authorized.");
            }

            model.addAttribute("sessionUser", userSession.getUser().getName());
            model.addAttribute("sessionProject", userSession.getProject().getName());
            return "home";
        } catch (NotAuthorizedException ex) {
            LOGGER.warn(ex.getMessage());
            return "index";
        } catch (Exception ex) {
            LOGGER.error("Server error.", ex);
            return "index";
        }
    }
}
