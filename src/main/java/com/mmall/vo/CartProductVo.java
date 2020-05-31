package com.mmall.vo;

import java.math.BigDecimal;

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
  private Boolean productStatus;
  private Integer productStock; // 产品库存
  private Integer productChecked;

  private String limitQuantity;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductSubtitle() {
    return productSubtitle;
  }

  public void setProductSubtitle(String productSubtitle) {
    this.productSubtitle = productSubtitle;
  }

  public String getProductMainImage() {
    return productMainImage;
  }

  public void setProductMainImage(String productMainImage) {
    this.productMainImage = productMainImage;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  public BigDecimal getProductTotalPrice() {
    return productTotalPrice;
  }

  public void setProductTotalPrice(BigDecimal productTotalPrice) {
    this.productTotalPrice = productTotalPrice;
  }

  public Boolean getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Boolean productStatus) {
    this.productStatus = productStatus;
  }

  public Integer getProductStock() {
    return productStock;
  }

  public void setProductStock(Integer productStock) {
    this.productStock = productStock;
  }

  public Integer getProductChecked() {
    return productChecked;
  }

  public void setProductChecked(Integer productChecked) {
    this.productChecked = productChecked;
  }

  public String getLimitQuantity() {
    return limitQuantity;
  }

  public void setLimitQuantity(String limitQuantity) {
    this.limitQuantity = limitQuantity;
  }
}
