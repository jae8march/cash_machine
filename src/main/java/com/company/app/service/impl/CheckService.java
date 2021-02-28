package com.company.app.service.impl;

import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import com.company.app.dao.impl.CheckDAO;
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;
import com.company.app.dao.interfaceDao.ICheckDAO;
import com.company.app.service.ICheckService;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to work with dao, check.
 */
public class CheckService implements ICheckService {
    private static final Logger LOG = Logger.getLogger(CheckService.class);
    private static final String SQL_COUNT = "SELECT count(*) AS count FROM checks";
    private String sqlCountProduct = "SELECT count(*) AS count FROM checks WHERE check_id=";
    private static final String SQL_COUNT_NEW_CHECKS = "SELECT count(*) AS count FROM checks WHERE checks_status='CREATED'";
    private static final String SQL_CHECK_DATE_TODAY = "SELECT SUM(checks_price) as sum FROM checks WHERE DATE(?) AND checks_status IN ('CREATED', 'CLOSED');";
    private static final String SQL_CHECK_DATE_BEFORE = "SELECT SUM(checks_price) as sum FROM checks WHERE checks_date < DATE(?) AND checks_status IN ('CLOSED')";
    FactoryDAO factory = FactoryDAO.getInstance();

    @Override
    public boolean create(Check entity) {
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            return checkDAO.newObject(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public long getAllNewChecks() {
        CheckDAO checkDAO = factory.getCheckDAO();
        return checkDAO.getCountSql(SQL_COUNT_NEW_CHECKS);
    }

    public Check getById(long id) {
        Check check = new Check();
        try (CheckDAO checkDAO = factory.getCheckDAO()) {
            check = checkDAO.findById(id);
        } catch (Exception e) {
            LOG.error(e);
        }
        return check;
    }

    public double findAllChecksTypeByData(Date date) {
        try (ICheckDAO checkDAO = factory.getCheckDAO()) {//SELECT SUM(checks_price) as sum FROM checks WHERE
            return checkDAO.findAllChecksByDate(date, SQL_CHECK_DATE_TODAY);
        } catch (Exception e) {
            LOG.error(e);
            return 0;
        }
    }

    public double findAllChecksTypeByDateBefore(Date date) {
        try (ICheckDAO checkDAO = factory.getCheckDAO()) {//SELECT SUM(checks_price) as sum FROM checks WHERE
            return checkDAO.findAllChecksByDate(date, SQL_CHECK_DATE_BEFORE);
        } catch (Exception e) {
            LOG.error(e);
            return 0;
        }
    }

    public Product getProductById(long id, long code) {
        Product product = new Product();
        try (CheckDAO checkDAO = factory.getCheckDAO()) {
            product = checkDAO.findProductById(id, code);
        } catch (Exception e) {
            LOG.error(e);
        }
        return product;
    }

    public double getAllFinishedChecks() {
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            return checkDAO.findAllFinishedChecks();
        } catch (Exception e) {
            LOG.error(e);
            return 0;
        }
    }

    public List<Check> getCheckSorted(String sortBy, long rows, long offset, User user){
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            checks = checkDAO.findNumberSorted(sortBy, rows, offset, user);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    public List<Product> getCheckProductSorted(long id, long rows, long offset){
        List<Product> products = new ArrayList<>();
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            products = checkDAO.findProductSorted(id, rows, offset);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    public List<Product> getAllProductInCheck(long id){
        List<Product> products = new ArrayList<>();
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            products = checkDAO.findAllProductInCheck(id);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    public boolean update(Check entity){
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            checkDAO.update(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public boolean updateContentCheck(Product entity){
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            checkDAO.updateContentCheck(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public boolean changeStatusCheck(long id, String status) {
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            checkDAO.changeStatus(id, status);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public long getCheckCount() {
        CheckDAO checkDAO = factory.getCheckDAO();
            return checkDAO.getCountSql(SQL_COUNT);
    }

    public long getCheckProductCount(long id) {
        CheckDAO checkDAO = factory.getCheckDAO();
        sqlCountProduct += id;
            return checkDAO.getCountSql(sqlCountProduct);
    }

    @Override
    public boolean delete(long id) {
        try (CheckDAO checkDAO = factory.getCheckDAO()){
            checkDAO.deleteProductInCheck(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }
}
