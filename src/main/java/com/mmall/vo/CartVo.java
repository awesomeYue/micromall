package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {

  List<CartProductVo> cartProductVoList;

  private BigDecimal cartTotalPrice;
  private String imageHost;
  private Boolean allChecked; // 是否已经都勾选
}
