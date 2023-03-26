package cn.himawari.cart.controller;


import cn.himawari.cart.service.CartService;
import cn.himawari.param.CartListParam;
import cn.himawari.param.CartSaveParam;
import cn.himawari.pojo.Cart;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result){
        log.info("进入路由save");
        if(result.hasErrors()){
            return R.fail("核心参数为null,添加失败！");
        }
        return  cartService.save(cartSaveParam);
    }

    @PostMapping("list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("购物车数据查询失败");
        }
        return  cartService.list(cartListParam.getUserId());
    }

    @PostMapping("update")
    public R update(@RequestBody Cart cart){

        return  cartService.update(cart);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Cart cart){

        return  cartService.remove(cart);
    }


}
