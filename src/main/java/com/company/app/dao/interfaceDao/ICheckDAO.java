package com.company.app.dao.interfaceDao;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Interface for DAO, check entity.
 */
public interface ICheckDAO extends IDAO<Check> {
    Logger LOG = Logger.getLogger(ICheckDAO.class);

    /**
     * Adds in table 'product_check' product.
     * @param product
     * @return
     */
    boolean updateContentCheck(Product product);

    /**
     * Find check.
     * @param id check
     * @return
     */
    Check findById(long id);

    /**
     * Finds product in checks.
     * @param id check
     * @param code product
     * @return
     */
    Product findProductById(long id, long code);

    /**
     * Delete product from check.
     * @param code product
     * @return
     */
    boolean deleteProductInCheck(long code);

    /**
     * Finds all product in check
     * @param id check
     * @return list products
     */
    List<Product> findAllProductInCheck(long id);

    /**
     * Changes status checks.
     * @param id check
     * @param status to which you want to change
     * @return true, if the status has been changed
     */
    boolean changeStatus(long id, String status);

    /**
     * Finds the number of rows in a table with a limit.
     * @param sortBy
     * @param integer
     * @param offset
     * @param user
     * @return sorted list
     */
    List<Check> findNumberSorted(String sortBy, long integer, long offset, User user);

    /**
     * Default method, finds all checks in the database in the specified interval.
     * @param date
     * @param sql request at a specified interval
     * @return
     */
    default double findAllChecksByDate(Date date, String sql) {
        Connection connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("sum");
            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            LOG.error("Cannot find all in database by date:" + date + e.getMessage());
            return -1;
        }
        return 0;
    }
}
