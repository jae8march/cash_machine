package com.company.app.dao.interfaceDao;
//TODO WORK
import com.company.app.dao.entity.Product;

import java.util.List;

public interface IProductDAO extends IDAO<Product> {
    public Product findProductName(String name);

    Product findById(long id);

    List<Product> findAll();

    List<Product> findAllSorted(String sortBy);

    List<Product> findNumberSorted(String sortBy, long integer, long offset);
}
