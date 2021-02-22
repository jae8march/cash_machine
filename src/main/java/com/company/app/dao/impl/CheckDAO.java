package com.company.app.dao.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.CheckStatus;
import com.company.app.dao.interfaceDao.ICheckDAO;
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
    private static CheckDAO instance;

    /** Queries for working with MySQL*/
    private static final String SQL_CREATE_CHECK = "INSERT INTO checks " +
            "(user_login) VALUES (?)";
    private static final String SQL_UPDATE_CONTENT = "INSERT INTO product_check " +
            "(checks_id, product_code, product_name, product_check_quantity, product_check_weight, product_weightSold, " +
            "check_product_price) VALUES (?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_PRICE = "UPDATE checks SET checks_price = ? where checks_id = ?";

    private static final String SQL_FIND_ALL_CHECKS = "SELECT * FROM checks";
    private static final String SQL_FIND_ALL_CHECKS_USER = "SELECT * FROM checks WHERE user_login=?";
    private static final String SQL_FIND_ALL_FINISHED_CHECKS = "SELECT * FROM checks WHERE checks_status='CLOSED'";
    private static final String SQL_FIND_ALL_NEW_CHECKS = "SELECT * FROM checks WHERE checks_status='CREATED'";
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM checks WHERE checks_id=?";
    private static final String SQL_FIND_ALL_PRODUCT = "SELECT * FROM product_check where checks_id = ?";

    private static final String SQL_FIND_PRODUCT_IN_CHECK = "SELECT * FROM product_check where checks_id = ? AND product_code=?";
    private static final String SQL_DELETE_PRODUCT_IN_CHECK = "DELETE FROM product_check WHERE product_code=?";

    private static final String SQL_CHANGE_STATUS_CHECK = "UPDATE checks SET checks_status=? WHERE checks_id=?";

    private static final String SQL_FIND_CHECKS_Z = "SELECT checks_price FROM checks WHERE checks_status='CLOSED'";

    private static final String SQL_SORT = " SELECT * FROM checks ORDER BY ?";
    private static final String SQL_SORT_ID = " SELECT * FROM checks ORDER BY checks_id+0 limit ? offset ?";
    private static final String SQL_SORT_DATE = " SELECT * FROM checks ORDER BY checks_date limit ? offset ?";
    private static final String SQL_SORT_STATUS = " SELECT * FROM checks ORDER BY checks_status limit ? offset ?";
    private static final String SQL_SORT_PRICE = " SELECT * FROM checks ORDER BY checks_price+0 limit ? offset ?";

    private static final String SQL_CHECK_DATE_TODAY = "SELECT * FROM checks WHERE DATE(?) AND checks_status IN ('CREATED', 'CLOSED');";
    private static final String SQL_CHECK_DATE_BEFORE = "SELECT * FROM checks WHERE checks_date < DATE(?) AND checks_status IN ('CLOSED')";

    public static CheckDAO getInstance() {
        if (instance == null) {
            instance = new CheckDAO();
        }
        return instance;
    }

    @Override
    public boolean newObject(Check entity) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_CHECK)){

            stmt.setString(1, entity.getUser().getLogin());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e){
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Check entity) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_PRICE)){
            stmt.setDouble(1, entity.getCheckPrice());
            stmt.setLong(2, entity.getCheckId());

            ResultSet rs = stmt.executeQuery();

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean updateContentCheck(Product entity){
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_CONTENT)){
            stmt.setLong(1, entity.getCheck().getCheckId());
            stmt.setLong(2, entity.getCode());
            stmt.setString(3, entity.getName());
            stmt.setLong(4, entity.getQuantity());
            stmt.setDouble(5, entity.getWeight());
            stmt.setBoolean(6, entity.isWeightSold());
            stmt.setDouble(7, entity.getPrice());

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Check> findAllChecksByDate(Timestamp date, String sql) {
        List<Check> checks = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setTimestamp(1, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                checks.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return checks;
    }

    public List<Check> findAllChecksTypeByDate(Timestamp date) {
        return findAllChecksByDate(date, SQL_CHECK_DATE_TODAY);
    }

    public List<Check> findAllChecksTypeByDateBefore(Timestamp date) {
        return findAllChecksByDate(date, SQL_CHECK_DATE_BEFORE);
    }

    @Override
    public boolean deleteObject(long id) {
        return false;
    }

    @Override
    public boolean closedCheck(long id) {
        return false;
    }

    public Check findById(long id) {
        Check check = new Check();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_CODE)){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            check = mapper(rs);

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return check;
    }

    public Product findProductById(long id, long code) {
        Product product = new Product();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_PRODUCT_IN_CHECK)){
            stmt.setLong(1, id);
            stmt.setLong(2,code);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            product.setCode(rs.getLong(Column.PRODUCT_CODE));
            product.setName(rs.getString(Column.PRODUCT_NAME));
            product.setPrice(rs.getDouble(Column.PRODUCT_PRICE));
            product.setQuantity(rs.getLong(Column.PRODUCT_QUANTITY));
            product.setWeight(rs.getDouble(Column.PRODUCT_WEIGHT));
            product.setWeightSold(rs.getBoolean(Column.PRODUCT_HOW_SOLD));

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return product;
    }

    public boolean deleteProductInCheck(long id) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_PRODUCT_IN_CHECK)){
            stmt.setLong(1, id);

            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Product> findAllProductInCheck(long id) {
        List<Product> products = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_PRODUCT)){
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setCode(resultSet.getLong(Column.PRODUCT_CODE));
                product.setName(resultSet.getString(Column.PRODUCT_NAME));
                product.setPrice(resultSet.getDouble(Column.PRODUCT_PRICE));
                product.setQuantity(resultSet.getLong(Column.PRODUCT_QUANTITY));
                product.setWeight(resultSet.getDouble(Column.PRODUCT_WEIGHT));
                product.setWeightSold(resultSet.getBoolean(Column.PRODUCT_HOW_SOLD));
                products.add(product);
            }

            close(resultSet);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return products;
    }

    public List<Check> findAllSorted(String sortBy) {
        List<Check> checks = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_SORT)){
            stmt.setString(1, sortBy);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                checks.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return checks;
    }

    public List<Check> findNumberSorted(String sortBy, long integer, long offset) {
        List<Check> checks = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try{
            PreparedStatement stmt;
            if(sortBy.equalsIgnoreCase("id")){
                stmt = connection.prepareStatement(SQL_SORT_ID);
            } else if(sortBy.equalsIgnoreCase("date")){
                stmt = connection.prepareStatement(SQL_SORT_DATE);
            } else if(sortBy.equalsIgnoreCase("price")){
                stmt = connection.prepareStatement(SQL_SORT_PRICE);
            } else if(sortBy.equalsIgnoreCase("status")){
                stmt = connection.prepareStatement(SQL_SORT_STATUS);
            } else {
                return checks;
            }
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


    @Override
    public List<Check> findAllChecksByUser(String userLogin) {
        List<Check> checks = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_CHECKS_USER)){
            stmt.setString(1, userLogin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                checks.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return checks;
    }

    @Override
    public List<Check> findAll() {
        return mapperList(SQL_FIND_ALL_CHECKS);
    }

    @Override
    public List<Check> findAllNewChecks() {
        return mapperList(SQL_FIND_ALL_NEW_CHECKS);
    }

    public List<Check> findAllFinishedChecks() {
        return mapperList(SQL_FIND_ALL_FINISHED_CHECKS);
    }

    public List<Check> findAllChecksZ() {
        return mapperList(SQL_FIND_CHECKS_Z);
    }

    @Override
    public boolean changeStatus(long id, String status) {
        connection = FactoryDAO.getConnection();
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

    @Override
    public List<Check> mapperList(String sql){
        List<Check> checks = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (Statement stmt = connection.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                checks.add(mapper(rs));
            }
            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return checks;
    }

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
