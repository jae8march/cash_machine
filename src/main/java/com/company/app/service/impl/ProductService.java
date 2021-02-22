package com.company.app.service.impl;
//TODO WORK
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Product;
import com.company.app.dao.impl.ProductDAO;
import com.company.app.service.IProductService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    private static final Logger LOG = Logger.getLogger(ProductService.class);
    private static final String SQL_COUNT = "SELECT count(*) as count from product";
    FactoryDAO factory = FactoryDAO.getInstance();

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (ProductDAO productDao = factory.getProductDAO()) {
            products = productDao.findAll();
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    @Override
    public boolean create(Product entity) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            return productDao.newObject(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    @Override
    public boolean delete(long code) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            productDao.deleteProduct(code);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            return productDao.update(product);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    @Override
    public Product getProductById(long id) {
        Product product = new Product();
        try (ProductDAO productDao = factory.getProductDAO()) {
            product = productDao.findById(id);
        } catch (Exception e) {
            LOG.error(e);
        }
        return product;
    }

    public List<Product> getProductsSorted(String sortBy, long rows, Long offset){
        List<Product> products = new ArrayList<>();
        try (ProductDAO productDao = factory.getProductDAO()) {
            products = productDao.findNumberSorted(sortBy, rows, offset);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    public List<Product> getProductsSortedBy(String sortBy){
        List<Product> products = new ArrayList<>();
        try (ProductDAO productDao = factory.getProductDAO()) {
            products = productDao.findAllSorted(sortBy);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    @Override
    public Product getProductName(String name) {
        Product product = new Product();
        try (ProductDAO productDao = factory.getProductDAO()) {
            product = productDao.findProductName(name);
        } catch (Exception e) {
            LOG.error(e);
        }
        return product;
    }

    @Override
    public long getProductCount() throws Exception {
        try (ProductDAO productDao = factory.getProductDAO()) {
            return productDao.getCountSql(SQL_COUNT);
        }
    }
}
