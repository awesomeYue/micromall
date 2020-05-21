package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.services.ProductService;
import com.mmall.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product, HttpSession session){
        final User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return productService.saveOrUpdate(product);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId,Integer status, HttpSession session){
        final User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return productService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(Integer productId, HttpSession session){
        final User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return productService.manageProductDetail(productId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        final User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return productService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        final User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return productService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
