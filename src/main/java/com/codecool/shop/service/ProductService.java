package com.codecool.shop.service;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private SupplierDao supplierDao;

    public ProductService(ProductDao productDao, CategoryDao categoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.supplierDao = supplierDao;
    }

    public ProductCategory getProductCategory(int categoryId){
        return categoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = categoryDao.find(categoryId);
        return productDao.getBy(category);
    }


    public Supplier getProductSupplier(int supplierId) {
        return supplierDao.find(supplierId);
    }

    public List<Product> getProductsForSupplier(int supplierId) {
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier);
    }


}
