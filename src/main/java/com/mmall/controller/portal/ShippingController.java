package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 收货地址功能的开发
 *
 * @author suny
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController {
  @Autowired ShippingService shippingService;

  @RequestMapping("add.do")
  @ResponseBody
  public ServerResponse add(HttpSession session, Shipping shipping) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return shippingService.add(user.getId(), shipping);
  }

  @RequestMapping("update.do")
  @ResponseBody
  public ServerResponse update(HttpSession session, Shipping shipping) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return shippingService.update(user.getId(), shipping);
  }

  @RequestMapping("delete.do")
  @ResponseBody
  public ServerResponse delete(HttpSession session, Integer shippingId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return shippingService.delete(user.getId(), shippingId);
  }

  @RequestMapping("select.do")
  @ResponseBody
  public ServerResponse viewInfo(HttpSession session, Integer shippingId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return shippingService.select(user.getId(), shippingId);
  }

  @RequestMapping("list.do")
  @ResponseBody
  public ServerResponse list(
      HttpSession session,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return shippingService.list(user.getId(), pageNum, pageSize);
  }
}
