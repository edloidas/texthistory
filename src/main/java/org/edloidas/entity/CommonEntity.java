package org.edloidas.entity;

import java.io.Serializable;

/**
 * Base Entity interface.
 * Extends some valuable interfaces.
 *
 * @author edloidas@gmail.com
 */
public abstract class CommonEntity implements Entity {
    public String toJson() {
        return "{}";
    }
}
