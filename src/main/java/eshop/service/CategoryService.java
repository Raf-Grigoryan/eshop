package eshop.service;

import eshop.model.Category;

import java.util.List;

public interface CategoryService {

    void add(Category category);

    Category findById(int id);

    List<Category> findAll();

    void removeCategoryById(int id);

}
