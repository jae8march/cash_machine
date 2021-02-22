package com.company.app.dao.interfaceDao;
//TODO WORK
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface IDAO<T> extends AutoCloseable{
    /**
     * Adds row in table if input entity is not already exist.
     * @param entity
     * @return true if was added
     */
    boolean newObject (T entity);

    /**
     * Updates the data for an entity in a table.
     * @param entity
     * @return true if the table has been updated
     */
    boolean update(T entity);

    /**
     * Removes an object by it's id.
     * @param id
     * @return true if row was deleted
     */
    boolean deleteObject(long id);

    default long getCountSql(String sql){
        Connection connection = FactoryDAO.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("count");
            }
            close(stmt);
            close(rs);
            connection.close();

            return 0;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Set data to the entity.
     * @param resultSet
     * @return entity
     */
    T mapper(ResultSet resultSet);

    /**
     * Closes the ResultSet.
     * @param rs
     */
    void close(ResultSet rs);

    /**
     * Closes the PreparedStatement.
     * @param ps
     */
    void close(PreparedStatement ps);

    /**
     * Closes the Statement.
     * @param st
     */
    void close(Statement st);
}
