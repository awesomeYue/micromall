package com.mmall.services;

import com.mmall.common.ServerResponse;

import java.util.Map;

public interface OrderService {

  ServerResponse pay(Integer userId, Long orderNo, String path);

  ServerResponse alipayCallback(Map<String, String> params);

  ServerResponse queryOrderStatus(Integer userId, Long orderNo);
}
