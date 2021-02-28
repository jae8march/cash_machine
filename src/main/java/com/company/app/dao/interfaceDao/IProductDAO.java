package com.company.app.dao.interfaceDao;

import com.company.app.dao.entity.Product;

import java.util.List;

/**
 * Interface for DAO, product entity.
 */
public interface IProductDAO extends IDAO<Product> {
    /**
     * Checks if there is a product on an create check
     * @param id
     * @return
     */
    boolean findProductInChecks(long id);

    /**
     * Finds the product by its code
     * @param name product
     * @return
     */
    Product findProductName(String name);

    /**
     * Finds the product by its code
     * @param code product
     * @return
     */
    Product findById(long code);

    /**
     * Finds the number of rows in a table with a limit
     * @param sortBy
     * @param integer
     * @param offset
     * @return sorted list
     */
    List<Product> findNumberSorted(String sortBy, long integer, long offset);
}
