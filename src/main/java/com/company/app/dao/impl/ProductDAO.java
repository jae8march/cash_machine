package com.company.app.dao.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Product;
import com.company.app.dao.interfaceDao.IProductDAO;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;
//TODO WORK
import java.sql.*;
import java.util.*;

/**
 * Extending class ProductDAO for working with a table 'product'.
 */
public class ProductDAO implements IProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductDAO.class);
    private Connection connection;
    private static ProductDAO instance;

    /** Queries for working with MySQL*/
    private static final String SQL_ADD_NEW_PRODUCT = "INSERT INTO product " +
            "(product_code, product_name, product_price, product_quantity, product_weight, product_weightSold) " +
            "VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_ALL_PRODUCT = "SELECT * FROM product";
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM product WHERE product_name=?";
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM product WHERE product_code=?";
    private static final String SQL_DELETE_BY_CODE = "DELETE FROM product WHERE product_code =?";
    private static final String SQL_UPDATE = "UPDATE product SET product_quantity=?, product_weight =? where product_code = ?";


    private static final String SQL_SORT = " SELECT * FROM product ORDER BY ?";
    private static final String SQL_SORT_CODE = " SELECT * FROM product ORDER BY product_code+0 limit ? offset ?";
    private static final String SQL_SORT_NAME = " SELECT * FROM product ORDER BY product_name limit ? offset ?";
    private static final String SQL_SORT_PRICE = " SELECT * FROM product ORDER BY product_price+0 limit ? offset ?";
    private static final String SQL_SORT_QUANTITY = " SELECT * FROM product ORDER BY product_quantity+0 limit ? offset ?";
    private static final String SQL_SORT_WEIGHT = " SELECT * FROM product ORDER BY product_weight+0 limit ? offset ?";

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(Product entity) {
        connection = FactoryDAO.getConnection();
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
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(Product entity) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE)){
            stmt.setLong(1, entity.getQuantity());
            stmt.setDouble(2, entity.getWeight());
            stmt.setLong(3, entity.getCode());

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Product findProductName(String name) {
        Product product = new Product();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_NAME)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            product = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_PRODUCT);

            while (rs.next()) {
                Product product;
                product = mapper(rs);
                products.add(product);
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return products;
    }

    @Override
    public Product findById(long id) {
        Product product = new Product();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_CODE)){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            product = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return product;
    }

    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        return false;
    }

    public boolean deleteProduct(long id) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_BY_CODE)){
            stmt.setLong(1, id);

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findAllSorted(String sortBy) {
        List<Product> products = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_SORT)){
            stmt.setString(1, sortBy);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapper(rs));
            }
            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findNumberSorted(String sortBy, long integer, long offset) {
        List<Product> products = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try{
            PreparedStatement stmt;
            if(sortBy.equalsIgnoreCase("code")){
                stmt = connection.prepareStatement(SQL_SORT_CODE);
            } else if(sortBy.equalsIgnoreCase("name")){
                stmt = connection.prepareStatement(SQL_SORT_NAME);
            } else if(sortBy.equalsIgnoreCase("price")){
                stmt = connection.prepareStatement(SQL_SORT_PRICE);
            } else if(sortBy.equalsIgnoreCase("quantity")){
                stmt = connection.prepareStatement(SQL_SORT_QUANTITY);
            } else if(sortBy.equalsIgnoreCase("weight")){
                stmt = connection.prepareStatement(SQL_SORT_WEIGHT);
            } else {
                return products;
            }
            stmt.setLong(1, integer);
            stmt.setLong(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return products;
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
            LOG.error(e.getMessage());
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
