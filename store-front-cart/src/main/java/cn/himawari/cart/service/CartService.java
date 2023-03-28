package cn.himawari.cart.service;

import cn.himawari.param.CartSaveParam;
import cn.himawari.pojo.Cart;
import cn.himawari.utils.R;

import java.util.List;

public interface CartService {

    /**
     * 购物车数据添加方法
     * @param cartSaveParam
     * @return 001成功 002已经存在 003没有库存
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 返回购物车数据
     * @param userId
     * @return
     */
    R list(Integer userId);


    /**
     * 更新购物车数据
     * @param cart
     * @return
     */
    R update(Cart cart);

    /**
     * 删除购物车数据
     * @param cart
     * @return
     */
    R remove(Cart cart);


    /**
     * 清空对应id的购物车中的商品
     * @param cartIds
     */
    void clearIds(List<Integer> cartIds);

    /**
     * 查询购物车项
     * @param productId
     * @return
     */
    R check(Integer productId);
}
