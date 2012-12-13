package org.jboss.errai.forge.enums;

/**
 * @author pslegr
 */
public enum ErraiUICommandsEnum {
    ERRAI_UI_COMPONENT("component"),
    ERRAI_UI_ADD_BINDING("add-binding");

    private String name;

    private ErraiUICommandsEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
