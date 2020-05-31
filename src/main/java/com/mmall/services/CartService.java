package com.mmall.services;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/** @author suny */
public interface CartService {

  ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

  ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

  ServerResponse<CartVo> delete(Integer userId, String productIds);

  ServerResponse<CartVo> list(Integer userId);

  ServerResponse selectOrUnselect(Integer userId, Integer productId, Integer checked);

  ServerResponse getCartProductCount(Integer userId);
}
