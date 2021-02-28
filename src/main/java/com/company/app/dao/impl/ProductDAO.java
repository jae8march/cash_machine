package com.company.app.dao.impl;

import com.company.app.dao.entity.Product;
import com.company.app.dao.interfaceDao.IProductDAO;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Extending class ProductDAO for working with a table 'product'.
 */
public class ProductDAO implements IProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductDAO.class);
    private Connection connection;

    /** Queries for working with MySQL*/
    private static final String SQL_ADD_NEW_PRODUCT = "INSERT INTO product " +
            "(product_code, product_name, product_price, product_quantity, product_weight, product_weightSold) " +
            "VALUES (?,?,?,?,?,?)";

    private static final String SQL_FIND_BY_NAME = "SELECT * FROM product WHERE product_name=?";
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM product WHERE product_code=?";
    private static final String SQL_DELETE_BY_CODE = "DELETE FROM product WHERE product_code =?";
    private static final String SQL_UPDATE = "UPDATE product SET product_quantity=?, product_weight =? where product_code = ?";

    private static final String SQL_SORT_HOW = "SELECT * FROM product ORDER BY ";
    private static final String SQL_SORT_LIMIT = " limit ? offset ?";

    private static final String SQL_CREATED_CHECKS_AND_PRODUCT = "SELECT * FROM product_check INNER JOIN checks c on product_check.checks_id = c.checks_id AND product_code=? AND checks_status='CREATED';";

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(Product entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_ADD_NEW_PRODUCT)){
            stmt.setLong(1, entity.getCode());
            stmt.setString(2, entity.getName());
            stmt.setDouble(3, entity.getPrice());
            stmt.setLong(4, entity.getQuantity());
            stmt.setDouble(5, entity.getWeight());
            stmt.setBoolean(6,entity.isWeightSold());

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot create row in database for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(Product entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE)){
            stmt.setLong(1, entity.getQuantity());
            stmt.setDouble(2, entity.getWeight());
            stmt.setLong(3, entity.getCode());

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot update row in table for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IProductDAO#findProductInChecks(long)}
     */
    @Override
    public boolean findProductInChecks(long id) {
        Product product;
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATED_CHECKS_AND_PRODUCT)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            rs.next();
            product = mapper(rs);

            close(rs);
            connection.close();
            if(product.getName()!=null){
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Cannot delete product in chek where code product:" + id + e.getMessage());
        }
        return false;
    }

    /**
     * {@link IProductDAO#findProductName(String)}
     */
    @Override
    public Product findProductName(String name) {
        Product product = new Product();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_NAME)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            product = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find entity by name:" + name + e.getMessage());
        }
        return product;
    }

    /**
     * {@link IProductDAO#findById(long)}
     */
    @Override
    public Product findById(long code) {
        Product product = new Product();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_CODE)){
            stmt.setLong(1, code);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            product = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find entity by id:" + code + e.getMessage());
        }
        return product;
    }

    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_BY_CODE)){
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
     * {@link IProductDAO#findNumberSorted(String, long, long)}
     * @param sortBy
     * @param integer
     * @param offset
     * @return
     */
    @Override
    public List<Product> findNumberSorted(String sortBy, long integer, long offset) {
        List<Product> products = new ArrayList<>();
        sortBy = getTypeSort(sortBy);
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_SORT_HOW).append(sortBy).append(SQL_SORT_LIMIT);
        try (PreparedStatement stmt = connection.prepareStatement(String.valueOf(sb))){
            stmt.setLong(1, integer);
            stmt.setLong(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find number sorted by" + sortBy + ":" + e.getMessage());
        }
        return products;
    }

    /**
     * Checks what type of sort to use.
     * @param sortBy
     * @return
     */
    private String getTypeSort(String sortBy){
        if(sortBy.equalsIgnoreCase("code")){
            return Column.PRODUCT_CODE;
        } else if(sortBy.equalsIgnoreCase("name")){
            return Column.PRODUCT_NAME;
        } else if(sortBy.equalsIgnoreCase("price")){
            return Column.PRODUCT_PRICE;
        } else if(sortBy.equalsIgnoreCase("quantity")){
            return Column.PRODUCT_QUANTITY;
        } else if(sortBy.equalsIgnoreCase("weight")) {
            return Column.PRODUCT_WEIGHT;
        } else {
            return Column.PRODUCT_CODE;
        }
    }

    /**
     * {@link IDAO#mapper(ResultSet)}
     */
    @Override
    public Product mapper(ResultSet resultSet){
        Product product = new Product();
        try {
        product.setCode(resultSet.getLong(Column.PRODUCT_CODE));
        product.setName(resultSet.getString(Column.PRODUCT_NAME));
        product.setPrice(resultSet.getDouble(Column.PRODUCT_PRICE));
        product.setQuantity(resultSet.getLong(Column.PRODUCT_QUANTITY));
        product.setWeight(resultSet.getDouble(Column.PRODUCT_WEIGHT));
        product.setWeightSold(resultSet.getBoolean(Column.PRODUCT_HOW_SOLD));
        } catch (SQLException e) {
            LOG.error("Cannot set data to the entity:" + e.getMessage());
        }
        return product;
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
