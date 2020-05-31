package com.mmall.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("shippingService")
public class ShippingServiceImpl implements ShippingService {
  @Autowired ShippingMapper shippingMapper;

  @Override
  public ServerResponse add(Integer userId, Shipping shipping) {
    shipping.setUserId(userId);
    int row = shippingMapper.insert(shipping);
    if (row > 0) {
      Map<String, Integer> resultMap = Maps.newHashMap();
      resultMap.put("shippingId", shipping.getId());
      return ServerResponse.createBySuccess("新建地址成功", resultMap);
    }
    return ServerResponse.createByErrorMessage("新建地址失败");
  }

  @Override
  public ServerResponse update(Integer userId, Shipping shipping) {
    shipping.setUserId(userId);
    int row = shippingMapper.updateByPrimaryKeyAndUserId(shipping);
    if (row > 0) {
      return ServerResponse.createBySuccess("更新地址成功");
    }
    return ServerResponse.createBySuccess("更新地址失败");
  }

  @Override
  public ServerResponse delete(Integer userId, Integer shippingId) {
    if (shippingId == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    int row = shippingMapper.deleteByPrimaryKeyAndUserId(userId, shippingId);
    if (row > 0) {
      return ServerResponse.createBySuccess("删除地址成功");
    }
    return ServerResponse.createBySuccess("删除地址失败");
  }

  @Override
  public ServerResponse select(Integer userId, Integer shippingId) {
    if (shippingId == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Shipping shipping = shippingMapper.selectByPrimaryKeyAndUserId(userId, shippingId);
    if (shipping != null) {
      return ServerResponse.createBySuccess(shipping);
    }
    return ServerResponse.createByErrorMessage("没有找到相关地址");
  }

  @Override
  public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pagesize) {
    PageHelper.startPage(pageNum, pagesize);
    List<Shipping> shippings = shippingMapper.selectByUserId(userId);
    PageInfo<Shipping> pageInfo = new PageInfo<>(shippings);
    return ServerResponse.createBySuccess(pageInfo);
  }
}
