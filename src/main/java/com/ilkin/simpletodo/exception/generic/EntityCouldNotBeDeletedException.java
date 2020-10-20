package com.ilkin.simpletodo.exception.generic;

public class EntityCouldNotBeDeletedException extends RuntimeException {

    public EntityCouldNotBeDeletedException(Class entityClass, long id) {
        super(entityClass.getSimpleName() + " with ID " + id + " Could Not be Deleted");
    }

    public EntityCouldNotBeDeletedException(Exception e, Class entityClass, long id) {
        super(entityClass.getSimpleName() + " with ID " + id + " Could Not be Deleted");
        setStackTrace(e.getStackTrace());
    }
}
