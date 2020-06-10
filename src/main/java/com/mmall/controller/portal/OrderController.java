package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired OrderService orderService;

  @RequestMapping("/ailipay_callpay.do")
  @ResponseBody
  public Object alipayCallback(HttpServletRequest request) {
    HashMap<String, String> params = Maps.newHashMap();
    Map<String, String[]> parameterMap = request.getParameterMap();
    for (Iterator iter = parameterMap.keySet().iterator(); iter.hasNext(); ) {
      String name = (String) iter.next();
      String[] values = parameterMap.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr += (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
      }
      params.put(name, valueStr);
    }
    logger.info(
        "支付宝回调：sign:{},trade_status:{},参数：{}",
        params.get("sign"),
        params.get("trade_status"),
        params.toString());
    // 非常重要，验证回调函数的正确性，是不是支付宝回调的
    params.remove("sign_type");
    try {
      boolean result =
          AlipaySignature.rsaCheckV2(
              params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
      if (!result) {
        return ServerResponse.createByErrorMessage("非法请求，验证未通过，再恶意请求我就报警了！");
      }
    } catch (AlipayApiException e) {
      logger.error("支付宝回调异常", e);
    }
    ServerResponse serverResponse = orderService.alipayCallback(params);
    if (serverResponse.isSuccess()) {
      return Const.AlipayCallback.RESPOSE_SUCCESS;
    }
    return Const.AlipayCallback.RESPOSE_FAILED;
  }

  @RequestMapping("/pay.do")
  @ResponseBody
  public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    String path = request.getSession().getServletContext().getRealPath("upload");
    return orderService.pay(user.getId(), orderNo, path);
  }

  @RequestMapping("/query_order_status.do")
  @ResponseBody
  public ServerResponse<Boolean> queryOrderStatus(HttpSession session, Long orderNo) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }
    return orderService.queryOrderStatus(user.getId(), orderNo);
  }
}
