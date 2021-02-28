package com.company.app.dao.interfaceDao;

import com.company.app.dao.entity.User;
import java.util.List;

/**
 * Interface for DAO, user entity.
 */
public interface IUserDAO extends IDAO<User> {
    /**
     * Finds user in the database by login and form.
     * @param login
     * @return user
     */
    User findUser(String login);

    /**
     * Finds the number of rows.
     * @param str for sql request
     * @param sql the amount to be counted
     * @return
     */
    long findCount(String str, String sql);

    /**
     * Finds the number of rows in a table with a limit
     * @param sortBy
     * @param integer
     * @param offset
     * @return sorted list
     */
    List<User> findNumberSorted(String sortBy, long integer, long offset);
}