package com.mmall.services;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface ProductService {

    ServerResponse saveOrUpdate(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum,int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServerResponse<ProductDetailVo> getProductDatil(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordOrCategoryId(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);

}
