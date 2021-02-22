package com.company.app.service;
//TODO WORK
import com.company.app.dao.entity.Product;

import java.util.List;

/**
 * Interface for Product service.
 */
public interface IProductService extends IService<Product>{
    Product getProductById(long id);

    Product getProductName(String name);

    boolean update(Product product);

    long getProductCount() throws Exception;

    //boolean update(Product product);
    List<Product> getProductsSorted(String sortBy, long rows, Long offset);
    List<Product> getProductsSortedBy(String sortBy);
}
