package fi.arcusys.j8examples.resource;

import fi.arcusys.j8examples.model.Thing;

import java.util.Map;

abstract class BaseResource {
    protected Map<Long, Thing> database;

    public BaseResource() { }

    public BaseResource(Map<Long, Thing> database) {
        this.database = database;
    }
}