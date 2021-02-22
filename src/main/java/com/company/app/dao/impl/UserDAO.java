package com.company.app.dao.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.dao.interfaceDao.IUserDAO;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extending class UserDAO for working with a table 'user'.
 */
public class UserDAO implements IUserDAO {
    private static final Logger LOG = Logger.getLogger(UserDAO.class);
    private Connection connection;
    private static UserDAO instance;

    /** Queries for working with MySQL*/
    private static final String SQL_REGISTRATION = "INSERT INTO user " +
            "(user_name, user_surname, user_login, user_password, role) VALUES (?,?,?,?,?)";
    private static final String SQL_LOGIN = "SELECT * FROM user WHERE user_login=? AND user_password=?";
    private static final String SQL_FIND_NAME = "SELECT user_name, user_surname FROM user  WHERE user_id=?";
    private static final String SQL_FIND_ALL_USER = "SELECT * FROM user";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE user_id=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET role = ? where user_id = ?";

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(User entity) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_REGISTRATION)){

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSurname());
            stmt.setString(3, entity.getLogin());
            stmt.setString(4, entity.getPassword());
            stmt.setString(5, String.valueOf(entity.getUserRole()));
            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IUserDAO#findUser(String, String)}
     */
    @Override
    public User findUser(String login, String password) {
        User user = null;
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_LOGIN)){
            stmt.setString(1,login);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            user = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return user;
    }

    /**
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(User entity) {
        long id = entity.getId();
        String role = entity.getUserRole().toString();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_USER)){

            stmt.setString(1, role);
            stmt.setLong(2, id);

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    /**
     * {@link IUserDAO#findAll()}
     * @return
     */
    @Override
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_USER);
            while (rs.next()) {
                users.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return users;
    }

    /**
     * {@link IUserDAO#findById(long)}
     */
    @Override
    public User findById(long id) {
        User user = new User();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_NAME)){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            rs.next();
            user = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return user;
    }

    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        ResultSet rs;
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_USER)) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IDAO#mapper(ResultSet)}
     */
    @Override
    public User mapper(ResultSet resultSet){
        User user = new User();
        try {
            user.setId(resultSet.getLong(Column.USER_ID));
            user.setName(resultSet.getString(Column.USER_NAME));
            user.setSurname(resultSet.getString(Column.USER_SURNAME));
            user.setLogin(resultSet.getString(Column.USER_LOGIN));
            user.setPassword(resultSet.getString(Column.USER_PASSWORD));
            user.setUserRole(UserRole.valueOf(resultSet.getString(Column.USER_ROLE)));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return user;
    }

    /**
     * {@link AutoCloseable#close()}
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@link IDAO#close(ResultSet)}
     */
    @Override
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * {@link IDAO#close(PreparedStatement)}
     */
    @Override
    public void close(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * {@link IDAO#close(Statement)}
     */
    @Override
    public void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
