package com.company.app.service;
//TODO WORK
import com.company.app.dao.entity.User;
import com.company.app.util.exception.ProjectException;

import java.util.List;

/**
 * Interface for User service.
 */
public interface IUserService extends IService<User>{
    User login(String login, String password) throws ProjectException;

    void update(User user);

}
