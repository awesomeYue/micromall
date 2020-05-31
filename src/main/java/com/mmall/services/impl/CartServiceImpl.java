package com.mmall.services.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.services.CartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

  @Autowired CartMapper cartMapper;
  @Autowired ProductMapper productMapper;

  @Override
  public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
    if (productId == null || count == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
    if (cart == null) {
      // 如果这个这个产品不存在这个购物车里，需要新增一个产品记录
      Cart cartItem = new Cart();
      cartItem.setUserId(userId);
      cartItem.setProductId(productId);
      cartItem.setChecked(Const.Cart.CHECKED);
      cartItem.setQuantity(count);
      cartMapper.insert(cartItem);
    } else {
      // 如果产品已经存在购物车里，则对应的数量增加
      count = cart.getQuantity() + count;
      cart.setQuantity(count);
      cartMapper.updateByPrimaryKeySelective(cart);
    }
    return this.list(userId);
  }

  @Override
  public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
    if (productId == null || count == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
    if (cart != null) {
      cart.setQuantity(count);
      cartMapper.updateByPrimaryKeySelective(cart);
    }
    return this.list(userId);
  }

  @Override
  public ServerResponse<CartVo> delete(Integer userId, String productIds) {
    List<String> productIdList = Splitter.on(",").splitToList(productIds);
    if (CollectionUtils.isEmpty(productIdList)) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    cartMapper.deleteByUserIdAndProductIds(userId, productIdList);
    return this.list(userId);
  }

  @Override
  public ServerResponse<CartVo> list(Integer userId) {
    CartVo cartVo = this.getCartVoLimit(userId);
    return ServerResponse.createBySuccess(cartVo);
  }

  @Override
  public ServerResponse selectOrUnselect(Integer userId, Integer productId, Integer checked) {
    if (checked == null) {
      return ServerResponse.createByErrorCodeMessage(
          ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    cartMapper.selectOrUnselect(userId, productId, checked);
    return this.list(userId);
  }

  @Override
  public ServerResponse getCartProductCount(Integer userId) {
    return null;
  }

  private CartVo getCartVoLimit(Integer userId) {
    CartVo cartVo = new CartVo();
    List<Cart> carts = cartMapper.selectByUserId(userId);
    List<CartProductVo> cartProductVos = Lists.newArrayList();
    BigDecimal cartTotalPrice = new BigDecimal("0");
    if (CollectionUtils.isNotEmpty(carts)) {
      for (Cart cart : carts) {
        CartProductVo cartProductVo = new CartProductVo();
        cartProductVo.setId(cart.getId());
        cartProductVo.setUserId(cart.getUserId());
        cartProductVo.setProductId(cart.getProductId());
        Product product = productMapper.selectByPrimaryKey(cart.getProductId());
        if (product != null) {
          cartProductVo.setProductName(product.getName());
          cartProductVo.setProductSubtitle(product.getSubtitle());
          cartProductVo.setProductMainImage(product.getMainImage());
          cartProductVo.setProductPrice(product.getPrice());
          // 判断库存
          int buyLimitCount = 0;
          if (product.getStock() >= cart.getQuantity()) {
            buyLimitCount = cart.getQuantity();
            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
          } else {
            buyLimitCount = product.getStock();
            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
            // 购物车中更新有效库存
            cart.setQuantity(buyLimitCount);
            cartMapper.updateByPrimaryKeySelective(cart);
          }
          cartProductVo.setQuantity(buyLimitCount);
          // 计算总价
          cartProductVo.setProductTotalPrice(
              BigDecimalUtil.mul(product.getPrice().doubleValue(), buyLimitCount));

          cartProductVo.setProductChecked(cart.getChecked());
        }
        if (cart.getChecked() == Const.Cart.CHECKED) {
          // 如果已经勾选，增加到整个购物车总价中
          cartTotalPrice =
              BigDecimalUtil.add(
                  cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
        }
        cartProductVos.add(cartProductVo);
      }
    }
    cartVo.setCartTotalPrice(cartTotalPrice);
    cartVo.setCartProductVoList(cartProductVos);
    cartVo.setAllChecked(this.getAllCheckedStatus(userId));
    cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

    return cartVo;
  }

  private boolean getAllCheckedStatus(Integer userId) {
    if (userId == null) {
      return false;
    }
    return cartMapper.selectCartProductAllCheckedStatusByUserId(userId) == 0;
  }
}
