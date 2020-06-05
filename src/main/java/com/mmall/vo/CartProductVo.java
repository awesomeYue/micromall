package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductVo {
  // 结合了购物车和产品的一个抽象对象
  private Integer id;
  private Integer userId;
  private Integer productId;
  private Integer quantity;
  private String productName;
  private String productSubtitle;
  private String productMainImage;
  private BigDecimal productPrice;
  private BigDecimal productTotalPrice;
  private Integer productStatus;
  private Integer productStock; // 产品库存
  private Integer productChecked;
  private String limitQuantity;
}
