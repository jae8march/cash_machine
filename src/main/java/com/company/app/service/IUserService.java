package com.company.app.service;

import com.company.app.dao.entity.User;

import java.util.List;

/**
 * Interface for User service.
 */
public interface IUserService extends IService<User>{
    /**
     * Find user by login.
     * @param login
     * @return
     */
    User login(String login);

    /**
     * Finds the number of rows in a table with a limit
     * @param sortBy
     * @param rows
     * @param offset
     * @return sorted list
     */
    List<User> findNumberSorted(String sortBy, long rows, long offset);

    /**
     * Finds the number of rows.
     * @param str for sql request
     * @param sql the amount to be counted
     * @return
     */
    long getCount(String str, String sql);
}
