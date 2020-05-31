package com.mmall.services;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

public interface ShippingService {

  ServerResponse add(Integer userId, Shipping shipping);

  ServerResponse update(Integer userId, Shipping shipping);

  ServerResponse delete(Integer userId, Integer shippingId);

  ServerResponse select(Integer userId, Integer shippingId);

  ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pagesize);
}
