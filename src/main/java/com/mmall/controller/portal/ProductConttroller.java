package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.services.ProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductConttroller {

  @Autowired ProductService productService;

  @RequestMapping("detail.do")
  @ResponseBody
  public ServerResponse<ProductDetailVo> detail(Integer productId) {
    return productService.getProductDatil(productId);
  }

  @RequestMapping("list.do")
  @ResponseBody
  public ServerResponse<PageInfo> list(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "categoryId", required = false) Integer categoryId,
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

    return productService.getProductByKeywordOrCategoryId(
        keyword, categoryId, pageNum, pageSize, orderBy);
  }
}
