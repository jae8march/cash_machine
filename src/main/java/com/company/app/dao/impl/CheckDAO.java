package com.company.app.dao.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.CheckStatus;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.dao.interfaceDao.ICheckDAO;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extending class ChecksDAO for working with tables 'checks' and 'product_check'.
 */
public class CheckDAO implements ICheckDAO {
    private static final Logger LOG = Logger.getLogger(CheckDAO.class);
    private Connection connection;

    /** Queries for working with MySQL*/
    private static final String SQL_CREATE_CHECK = "INSERT INTO checks " +
            "(user_login) VALUES (?)";
    private static final String SQL_UPDATE_CONTENT = "INSERT INTO product_check " +
            "(checks_id, product_code, product_name, product_check_quantity, product_check_weight, product_weightSold, " +
            "check_product_price) VALUES (?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_PRICE = "UPDATE checks SET checks_price = ? where checks_id = ?";

    private static final String SQL_FIND_ALL_FINISHED_CHECKS = "SELECT SUM(checks_price) as sum FROM checks WHERE checks_status='CLOSED'";
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM checks WHERE checks_id=?";

    private static final String SQL_FIND_PRODUCT_IN_CHECK = "SELECT * FROM product_check where checks_id = ? AND product_code=?";
    private static final String SQL_DELETE_PRODUCT_IN_CHECK = "DELETE FROM product_check WHERE product_code=?";

    private static final String SQL_CHANGE_STATUS_CHECK = "UPDATE checks SET checks_status=? WHERE checks_id=?";

    private static final String SQL_SORT_HOW = "SELECT * FROM checks";
    private static final String SQL_SORT_LOGIN = " WHERE user_login=";
    private static final String SQL_SORT_ORDER = " ORDER BY ";
    private static final String SQL_SORT_LIMIT = " limit ? offset ?";

    private static final String SQL_FIND_ALL_PRODUCT_CHECK = "SELECT * FROM product_check where checks_id = ? limit ? offset ?";
    private static final String SQL_FIND_ALL_PRODUCT = "SELECT * FROM product_check where checks_id = ?";

    public CheckDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(Check entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_CHECK)){

            stmt.setString(1, entity.getUser().getLogin());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e){
            LOG.error("Cannot create row in database for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Updates check total price.
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(Check entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_PRICE)){
            stmt.setDouble(1, entity.getCheckPrice());
            stmt.setLong(2, entity.getCheckId());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot update row in table for entity:" + entity + e.getMessage());
            return false;
        }
        return true;
    }


    public boolean updateContentCheck(Product product){
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_CONTENT)){
            stmt.setLong(1, product.getCheck().getCheckId());
            stmt.setLong(2, product.getCode());
            stmt.setString(3, product.getName());
            stmt.setLong(4, product.getQuantity());
            stmt.setDouble(5, product.getWeight());
            stmt.setBoolean(6, product.isWeightSold());
            stmt.setDouble(7, product.getPrice());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot update content in table for entity:" + product + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        return false;
    }

    /**
     * {@link ICheckDAO#findById(long)}
     */
    public Check findById(long id) {
        Check check = new Check();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_CODE)){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            check = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find entity by id:" + id + e.getMessage());
        }
        return check;
    }

    /**
     * {@link ICheckDAO#findProductById(long, long)}
     */
    public Product findProductById(long id, long code) {
        Product product = new Product();
        Check check = new Check();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_PRODUCT_IN_CHECK)){
            stmt.setLong(1, id);
            stmt.setLong(2,code);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            check.setCheckId(rs.getLong(Column.CHECK_ID));
            product.setCode(rs.getLong(Column.PRODUCT_CODE));
            product.setName(rs.getString(Column.PRODUCT_NAME));
            product.setQuantity(rs.getLong(Column.CHECK_PRODUCT_QUANTITY));
            product.setWeight(rs.getDouble(Column.CHECK_PRODUCT_WEIGHT));
            product.setWeightSold(rs.getBoolean(Column.PRODUCT_HOW_SOLD));
            product.setPrice(rs.getDouble(Column.CHECK_PRODUCT_PRICE));

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find product by code:" + code + "and check id:" + id + e.getMessage());
        }
        return product;
    }

    /**
     * {@link ICheckDAO#deleteProductInCheck(long)}
     */
    public boolean deleteProductInCheck(long code) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_PRODUCT_IN_CHECK)){
            stmt.setLong(1, code);

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot delete product in chek where code product:" + code + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * {@link ICheckDAO#findAllProductInCheck(long)}
     */
    public List<Product> findAllProductInCheck(long id) {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_PRODUCT)){
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                Check check = new Check();

                check.setCheckId(resultSet.getLong(Column.CHECK_ID));
                product.setCheck(check);

                product.setCode(resultSet.getLong(Column.PRODUCT_CODE));
                product.setName(resultSet.getString(Column.PRODUCT_NAME));
                product.setQuantity(resultSet.getLong(Column.CHECK_PRODUCT_QUANTITY));
                product.setWeight(resultSet.getDouble(Column.CHECK_PRODUCT_WEIGHT));
                product.setWeightSold(resultSet.getBoolean(Column.PRODUCT_HOW_SOLD));
                product.setPrice(resultSet.getDouble(Column.CHECK_PRODUCT_PRICE));
                products.add(product);
            }

            close(resultSet);
            connection.close();
        } catch (SQLException e) {
            LOG.error("Cannot find all product in check:" + e.getMessage());
        }
        return products;
    }

    /**
     * {@link ICheckDAO#findNumberSorted(String, long, long, User)}
     */
    public List<Check> findNumberSorted(String sortBy, long integer, long offset, User user) {
        List<Check> checks = new ArrayList<>();
        sortBy = getTypeSort(sortBy);
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_SORT_HOW);
        if(user.getUserRole() == UserRole.CASHIER){
            sb.append(SQL_SORT_LOGIN).append("'").append(user.getLogin()).append("'");
        }
        sb.append(SQL_SORT_ORDER).append(sortBy).append(SQL_SORT_LIMIT);
        try (PreparedStatement stmt = connection.prepareStatement(String.valueOf(sb))){
            stmt.setLong(1, integer);
            stmt.setLong(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                checks.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return checks;
    }

    /**
     * Checks what type of sort to use.
     * @param sortBy
     * @return
     */
    private String getTypeSort(String sortBy){
        if(sortBy.equalsIgnoreCase("id")){
            return Column.CHECK_ID;
        } else if(sortBy.equalsIgnoreCase("date")){
            return Column.CHECK_DATA;
        } else if(sortBy.equalsIgnoreCase("price")){
            return Column.CHECK_PRICE;
        } else if(sortBy.equalsIgnoreCase("status")){
            return Column.CHECK_STATUS;
        } else {
            return Column.CHECK_ID;
        }
    }

    public List<Product> findProductSorted(long id, long integer, long offset) {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_PRODUCT_CHECK)){
            stmt.setLong(1, id);
            stmt.setLong(2, integer);
            stmt.setLong(3, offset);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                Check check = new Check();

                check.setCheckId(resultSet.getLong(Column.CHECK_ID));
                product.setCheck(check);

                product.setCode(resultSet.getLong(Column.PRODUCT_CODE));
                product.setName(resultSet.getString(Column.PRODUCT_NAME));
                product.setQuantity(resultSet.getLong(Column.CHECK_PRODUCT_QUANTITY));
                product.setWeight(resultSet.getDouble(Column.CHECK_PRODUCT_WEIGHT));
                product.setWeightSold(resultSet.getBoolean(Column.PRODUCT_HOW_SOLD));
                product.setPrice(resultSet.getDouble(Column.CHECK_PRODUCT_PRICE));
                products.add(product);
            }

            close(resultSet);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return products;
    }

    public double findAllFinishedChecks() {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_FINISHED_CHECKS)){
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("sum");
            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

    /**
     * {@link ICheckDAO#changeStatus(long, String)}
     */
    @Override
    public boolean changeStatus(long id, String status) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CHANGE_STATUS_CHECK)){
            stmt.setString(1,status);
            stmt.setLong(2, id);
            if (stmt.executeUpdate() != 1) {
                return false;
            }
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
    public Check mapper(ResultSet resultSet){
        Check check = new Check();
        User user = new User();
        try {
            check.setCheckId(resultSet.getLong(Column.CHECK_ID));
            check.setCheckDate(resultSet.getTimestamp(Column.CHECK_DATA));
            check.setCheckStatus(CheckStatus.valueOf(resultSet.getString(Column.CHECK_STATUS)));
            user.setLogin(resultSet.getString(Column.CHECK_LOGIN));
            check.setUser(user);
            check.setCheckPrice(resultSet.getLong(Column.CHECK_PRICE));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return check;
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
            LOG.error(e.getMessage());
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
