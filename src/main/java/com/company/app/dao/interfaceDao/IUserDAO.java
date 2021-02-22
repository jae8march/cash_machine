package com.company.app.dao.interfaceDao;
//TODO WORK
import com.company.app.dao.entity.User;

import java.util.List;

public interface IUserDAO extends IDAO<User> {
    /**
     * Finds user in the database by login and form.
     * @param login
     * @param password
     * @return user
     */
    User findUser(String login, String password);

    /**
     * Finds user in the database by id.
     * @param id
     * @return user
     */
    User findById(long id);

    /**
     * Finds all users in the database
     * @return hashset of users
     */
    List<User> findAll();
}