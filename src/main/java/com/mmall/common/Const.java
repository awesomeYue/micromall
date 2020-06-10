package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public final class Const {
  public static final String CURRENT_USER = "CURRENT_USER";
  public static final String EMAIL = "email";
  public static final String USERNAME = "username";

  private Const() {}

  public enum ProductStatusEnum {
    ON_SEAL(1, "在线");

    private String value;
    private int code;

    ProductStatusEnum(int code, String value) {
      this.code = code;
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public int getCode() {
      return code;
    }
  }

  public enum OrderStatusEnum {
    CANCALE(0, "已取消"),
    NO_PAY(10, "未支付"),
    PAIED(20, "已支付"),
    SIPPEND(40, "已发货"),
    ORDER_SUCCESS(50, "订单成功"),
    ORDER_CLOSE(60, "订单关闭");

    private String value;
    private int code;

    OrderStatusEnum(int code, String value) {
      this.code = code;
      this.value = value;
    }

    public int getCode() {
      return code;
    }

    public String getValue() {
      return value;
    }
  }

  public enum PayPlatformEnum {
    ALIPAY(1, "支付宝"),
    WECHATPAY(2, "微信支付");

    int code;
    String value;

    PayPlatformEnum(int code, String value) {
      this.code = code;
      this.value = value;
    }

    public int getCode() {
      return code;
    }

    public String getValue() {
      return value;
    }
  }

  public interface Role {
    int ROLE_CUSTOMER = 0; // 普通用户
    int ROLE_ADMIN = 1; // 管理员
  }

  public interface Cart {
    int CHECKED = 1;
    int UN_CHECKED = 0;

    String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
    String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
  }

  public interface ProductOrderBy {
    Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
  }

  public interface AlipayCallback {
    String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    String TRADE_CLOSED = "TRADE_CLOSED";
    String TRADE_SUCCESS = "TRADE_SUCCESS";
    String TRADE_FINISHED = "TRADE_FINISHED";

    String RESPOSE_SUCCESS = "SUCCESS";
    String RESPOSE_FAILED = "FAILED";
  }
}
