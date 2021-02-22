package com.company.app.service.impl;
//TODO WORK
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.User;
import com.company.app.dao.impl.UserDAO;
import com.company.app.service.IUserService;
import com.company.app.util.valid.Hash;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    static FactoryDAO factory = FactoryDAO.getInstance();

    @Override
    public boolean create(User user) {
//        user.setPassword(Hash.hashString(user.getPassword()));
        try (UserDAO userDAO = factory.getUserDAO()){
            return userDAO.newObject(user);
        }
        catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    @Override
    public User login(String login, String password) {
        User user = new User(login, password);
        try (UserDAO userDAO = factory.getUserDAO()){
            user = userDAO.findUser(login, password);
        } catch (Exception e) {
            LOG.error(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (UserDAO userDAO = factory.getUserDAO()){
            users = userDAO.findAll();
        } catch (Exception e) {
            LOG.error(e);
        }
        return users;
    }

    @Override
    public void update(User user) {
        try (UserDAO userDao = factory.getUserDAO()) {
            userDao.update(user);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try (UserDAO userDao = factory.getUserDAO()) {
            userDao.deleteObject(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
         return true;
    }
}