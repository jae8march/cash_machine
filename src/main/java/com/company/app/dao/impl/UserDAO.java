package com.company.app.dao.impl;

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

    /** Queries for working with MySQL*/
    private static final String SQL_REGISTRATION = "INSERT INTO user " +
            "(user_name, user_surname, user_login, user_password, role) VALUES (?,?,?,?,?)";

    private static final String SQL_LOGIN = "SELECT * FROM user WHERE user_login=?";
    private static final String SQL_GET_COUNT = "SELECT count(*) as count FROM user WHERE role=?";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE user_id=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET user_name=?, user_surname=?, role=? where user_id=?";

    private static final String SQL_SORT_HOW = "SELECT * FROM user ORDER BY ";
    private static final String SQL_SORT_LIMIT = " limit ? offset ?";

    private static final String SQL_FIND_CASHIER_CREATED_CHECK =
            "SELECT count(*) as count from checks WHERE user_login=? AND checks_status='CREATED'";

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(User entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_REGISTRATION)){

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSurname());
            stmt.setString(3, entity.getLogin());
            stmt.setString(4, entity.getPassword());
            stmt.setString(5, String.valueOf(entity.getUserRole()));
            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot create row in database for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IUserDAO#findUser(String)}
     */
    @Override
    public User findUser(String login) {
        User user = null;
        try (PreparedStatement stmt = connection.prepareStatement(SQL_LOGIN)){
            stmt.setString(1,login);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            user = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find entity by login:" + login + e.getMessage());
        }
        return user;
    }

    /**
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(User entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_USER)){
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSurname());
            stmt.setString(3, String.valueOf(entity.getUserRole()));
            stmt.setLong(4, entity.getId());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot update row in table for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IUserDAO#findCount(String, String)}
     */
    public long findCount(String str, String sql){
        if(sql.equalsIgnoreCase("role")){
            sql=SQL_GET_COUNT;
        } else if (sql.equalsIgnoreCase("cashier") ){
            sql=SQL_FIND_CASHIER_CREATED_CHECK;
        }
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, str);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("count");
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find count of objects:" + e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * {@link IUserDAO#findNumberSorted(String, long, long)}
     */
    public List<User> findNumberSorted(String sortBy, long integer, long offset) {
        List<User> users = new ArrayList<>();
        if(sortBy.equalsIgnoreCase("id")){
            sortBy = Column.USER_ID;
        } else if(sortBy.equalsIgnoreCase("name")){
            sortBy = Column.USER_NAME;
        } else if(sortBy.equalsIgnoreCase("surname")){
            sortBy = Column.USER_SURNAME;
        } else if(sortBy.equalsIgnoreCase("login")){
            sortBy = Column.USER_LOGIN;
        } else if(sortBy.equalsIgnoreCase("role")){
            sortBy = Column.USER_ROLE;
        } else {
            return users;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_SORT_HOW).append(sortBy).append(SQL_SORT_LIMIT);
        try (PreparedStatement stmt = connection.prepareStatement(String.valueOf(sb))){
            stmt.setLong(1, integer);
            stmt.setLong(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find number sorted by" + sortBy + ":" + e.getMessage());
        }
        return users;
    }


    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_USER)) {
            stmt.setLong(1, id);

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot delete object:" + e.getMessage());
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
            LOG.error("Cannot set data to the entity:" + e.getMessage());
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
            LOG.error("Error while closing the Connection:" + e.getMessage());
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
                LOG.error("Error while closing the ResultSet:" + e.getMessage());
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
                LOG.error("Error while closing the PreparedStatement:" + e.getMessage());
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
                LOG.error("Error while closing the Statement:" + e.getMessage());
            }
        }
    }
}
