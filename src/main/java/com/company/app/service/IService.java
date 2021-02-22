package com.company.app.service;
//TODO WORK
import com.company.app.dao.entity.User;
import com.company.app.util.exception.ProjectException;

import java.util.List;

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
     * Gets all data from table.
     * @return List of objects
     */
    List<T> getAll();

    /**
     * Add new entity in database.
     * @param entity
     * @return true if object was created
     * @throws ProjectException
     */
    boolean create(T entity) throws ProjectException;
}
