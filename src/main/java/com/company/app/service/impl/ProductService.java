package com.company.app.service.impl;

import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Product;
import com.company.app.dao.impl.ProductDAO;
import com.company.app.service.IProductService;
import com.company.app.service.IService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to work with dao, product.
 */
public class ProductService implements IProductService {
    private static final Logger LOG = Logger.getLogger(ProductService.class);
    private static final String SQL_COUNT = "SELECT count(*) as count from product";
    FactoryDAO factory = FactoryDAO.getInstance();

    /**
     * {@link IService#create(Object)}
     */
    @Override
    public boolean create(Product entity) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            return productDao.newObject(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * {@link IService#delete(long)}
     */
    @Override
    public boolean delete(long id) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            productDao.deleteObject(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    /**
     * {@link IProductService#update(Product)}
     */
    @Override
    public boolean update(Product product) {
        try (ProductDAO productDao = factory.getProductDAO()) {
            return productDao.update(product);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * {@link IProductService#findInCreatedCheckProduct(long)}
     */
    @Override
    public boolean findInCreatedCheckProduct(long id) {
        try (ProductDAO productDao = factory.getProductDAO()){
            return productDao.findProductInChecks(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * {@link IProductService#getProductById(long)}
     */
    @Override
    public Product getProductById(long code) {
        Product product = new Product();
        try (ProductDAO productDao = factory.getProductDAO()) {
            product = productDao.findById(code);
        } catch (Exception e) {
            LOG.error(e);
        }
        return product;
    }

    /**
     * {@link IProductService#getProductsSorted(String, long, long)}
     */
    @Override
    public List<Product> getProductsSorted(String sortBy, long rows, long offset){
        List<Product> products = new ArrayList<>();
        try (ProductDAO productDao = factory.getProductDAO()) {
            products = productDao.findNumberSorted(sortBy, rows, offset);
        } catch (Exception e) {
            LOG.error(e);
        }
        return products;
    }

    /**
     * {@link IProductService#getProductName(String)}
     */
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

    /**
     * {@link IProductService#getProductCount()}
     */
    @Override
    public long getProductCount() {
        ProductDAO productDao = factory.getProductDAO();
        return productDao.getCountSql(SQL_COUNT);
    }
}
