package org.edloidas.entity;

import java.io.Serializable;

/**
 * Base Entity interface.
 * Extends some valuable interfaces.
 *
 * @author edloidas@gmail.com
 */
public interface Entity extends Serializable {
    public String toJson();
}
