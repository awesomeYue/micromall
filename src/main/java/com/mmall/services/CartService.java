package com.mmall.services;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

public interface CartService {

  ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
}
