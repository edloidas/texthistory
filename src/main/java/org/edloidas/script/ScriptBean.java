package org.edloidas.script;

import org.apache.log4j.Logger;
import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.User;
import org.edloidas.web.service.EntityService;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * ScriptBean, that runs scripts after application context done loading.
 *
 * @author edloidas
 * @see org.springframework.beans.factory.InitializingBean
 */
public class ScriptBean implements InitializingBean {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(ScriptBean.class);

    /* USE Interface here.
     * Otherwise SPRING will throw BeanNotOfRequiredTypeException.
     * Spring uses the interface type to make dependency injection!
     */
    @Resource(name = "userService")
    private EntityService<User> userService;

    @Resource(name = "projectService")
    private EntityService<Project> projectService;

    /** Adds admin into database. */
    public void afterPropertiesSet() {
        try {

            User user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            user.setName("administrator@th.com");
            userService.addEntity(user);

        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
