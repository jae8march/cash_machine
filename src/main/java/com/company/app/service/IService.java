package com.company.app.service;

/**
 * Interface for service.
 * @param <T>
 */
public interface IService<T> {
    /**
     * Deletes object by id.
     * @param id
     * @return true if object was deleted
     */
    boolean delete(long id);

    /**
     * Add new entity in database.
     * @param entity
     * @return true if object was created
     */
    boolean create(T entity);
}
