package com.company.app.service;

import com.company.app.dao.entity.Product;

import java.util.List;

/**
 * Interface for Product service.
 */
public interface IProductService extends IService<Product>{
    /**
     * Finds the product by its code.
     * @param code product
     * @return
     */
    Product getProductById(long code);

    /**
     * Finds the product by its code.
     * @param name product
     * @return
     */
    Product getProductName(String name);

    /**
     * Updates the data for a product in a table.
     * @param product
     * @return true if the table has been updated
     */
    boolean update(Product product);

    /**
     * Counting products in a table.
     * @return
     */
    long getProductCount();

    /**
     * Checks if there is a product on an create check.
     * @param id
     * @return
     */
    boolean findInCreatedCheckProduct(long id);

    /**
     * Finds the number of rows in a table with a limit.
     * @param sortBy
     * @param rows
     * @param offset
     * @return sorted list
     */
    List<Product> getProductsSorted(String sortBy, long rows, long offset);
}
