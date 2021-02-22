package com.company.app.service.impl;
//TODO WORK
import com.company.app.dao.entity.Product;
import com.company.app.dao.impl.CheckDAO;
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Check;
import com.company.app.service.ICheckService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckService implements ICheckService {
    private static final Logger LOG = Logger.getLogger(CheckService.class);
    private static final String SQL_COUNT = "SELECT count(*) as count from checks";
    FactoryDAO factoryDAO = FactoryDAO.getInstance();

    @Override
    public List<Check> getAllNewChecks() {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findAllNewChecks();
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    public Check getById(long id) {
        Check check = new Check();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()) {
            check = checkDAO.findById(id);
        } catch (Exception e) {
            LOG.error(e);
        }
        return check;
    }

    public List<Check> findAllChecksTypeByData(Timestamp date) {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()) {
            checks = checkDAO.findAllChecksTypeByDate(date);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }
    public List<Check> findAllChecksTypeByDateBefore(Timestamp date) {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()) {
            checks = checkDAO.findAllChecksTypeByDateBefore(date);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    public Product getProductById(long id, long code) {
        Product product = new Product();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()) {
            product = checkDAO.findProductById(id, code);
        } catch (Exception e) {
            LOG.error(e);
        }
        return product;
    }

    @Override
    public List<Check> getAll() {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findAll();
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    @Override
    public List<Check> getAllFinishedChecks() {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findAllFinishedChecks();
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    @Override
    public List<Check> getAllChecksByUser(String userLogin) {
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findAllChecksByUser(userLogin);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    @Override
    public boolean create(Check check) {
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            return checkDAO.newObject(check);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public List<Check> getCheckSorted(String sortBy, long rows, Long offset){
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findNumberSorted(sortBy, rows, offset);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }
    public List<Product> getAllProductInCheck(long id){
        List<Product> products = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            products = checkDAO.findAllProductInCheck(id);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    public List<Check> getCheckSortedBy(String sortBy){
        List<Check> checks = new ArrayList<>();
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checks = checkDAO.findAllSorted(sortBy);
        } catch (Exception e) {
            LOG.error(e);
        }
        return checks;
    }

    public boolean update(Check entity){
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checkDAO.update(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public boolean updateContentCheck(Product entity){
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checkDAO.updateContentCheck(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public boolean changeStatusCheck(long id, String status) {
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checkDAO.changeStatus(id, status);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    public long getCheckCount() throws Exception {
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            return checkDAO.getCountSql(SQL_COUNT);
        }
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
    public boolean deleteProductInCheck(long id) {
        try (CheckDAO checkDAO = factoryDAO.getCheckDAO()){
            checkDAO.deleteProductInCheck(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }
}
