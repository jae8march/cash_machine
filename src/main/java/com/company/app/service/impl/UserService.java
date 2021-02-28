package com.company.app.service.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.User;
import com.company.app.dao.impl.UserDAO;
import com.company.app.service.IUserService;
import com.company.app.service.IService;
import com.company.app.util.valid.Hash;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to work with dao, user.
 */
public class UserService implements IUserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    FactoryDAO factory = FactoryDAO.getInstance();

    /**
     * {@link IService#create(Object)}
     */
    @Override
    public boolean create(User entity) {
        String hash = Hash.hashString(entity.getPassword());
        entity.setPassword(hash);
        try (UserDAO userDAO = factory.getUserDAO()) {
            return userDAO.newObject(entity);
        }
        catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * {@link IUserService#login(String)}
     */
    @Override
    public User login(String login) {
        User user = new User();
        try (UserDAO userDAO = factory.getUserDAO()){
            user = userDAO.findUser(login);
        } catch (Exception e) {
            LOG.error(e);
        }
        return user;
    }

    /**
     * {@link IUserService#findNumberSorted(String, long, long)}
     */
    public List<User> findNumberSorted(String sortBy, long rows, long offset){
        List<User> users = new ArrayList<>();
        try (UserDAO userDAO = factory.getUserDAO()){
            users = userDAO.findNumberSorted(sortBy, rows, offset);
        } catch (Exception e) {
            LOG.error(e);
        }
        return users;
    }

    /**
     * {@link IUserService#getCount(String, String)}
     */
    public long getCount(String str, String sql) {
        long count = 0;
        try (UserDAO userDAO = factory.getUserDAO()){
            count = userDAO.findCount(str, sql);
        } catch (Exception e) {
            LOG.error(e);
        }
        return count;
    }

    /**
     * {@link IService#delete(long)}
     */
    @Override
    public boolean delete(long id) {
        try (UserDAO userDao = factory.getUserDAO()) {
            return userDao.deleteObject(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }
}