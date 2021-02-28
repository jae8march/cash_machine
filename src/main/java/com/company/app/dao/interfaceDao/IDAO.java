package com.company.app.dao.interfaceDao;

import com.company.app.dao.connection.FactoryDAO;

import java.sql.*;

/**
 * Interface for DAO.
 * @param <T>
 */
public interface IDAO<T> extends AutoCloseable{
    /**
     * Method for counting rows in a table.
     * @param sql
     * @return
     */
    default long getCountSql(String sql){
        Connection connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("count");
            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

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
