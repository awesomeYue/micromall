package com.mmall.services;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface CategoryService {

  ServerResponse addCategory(String cateName, Integer parentId);

  ServerResponse updateCategory(String cateName, Integer parentId);

  ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

  ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
