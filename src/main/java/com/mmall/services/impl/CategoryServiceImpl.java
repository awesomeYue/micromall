package com.mmall.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.services.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String cateName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(cateName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误！");
        }
        Category category=new Category();
        category.setParentId(parentId);
        category.setName(cateName);
        category.setStatus(true);
        int row=categoryMapper.insert(category);
        if(row>0) {
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategory(String cateName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(cateName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误！");
        }
        Category category=new Category();
        category.setParentId(parentId);
        category.setName(cateName);
        category.setUpdateTime(new Date());
        int row=categoryMapper.updateByPrimaryKeySelective(category);
        if(row>0) {
            return ServerResponse.createBySuccessMessage("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> list=categoryMapper.selectChildrenParallelCategory(categoryId);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("查询的品类为空");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer>categoryIdList=Lists.newArrayList();
        if (categoryId == null) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //递归算法，算出字节点
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找字节点，递归算法一定要有一个退出机制
        List<Category> categoryList=categoryMapper.selectChildrenParallelCategory(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
