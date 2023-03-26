package cn.himawari.cart.service.impl;

import cn.himawari.cart.mapper.CartMapper;
import cn.himawari.cart.service.CartService;
import cn.himawari.clients.ProductClient;
import cn.himawari.param.CartSaveParam;
import cn.himawari.param.ProductCollectParam;
import cn.himawari.param.ProductIdParam;
import cn.himawari.pojo.Cart;
import cn.himawari.pojo.Product;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.himawari.vo.CartVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class cartServiceImpl implements CartService {
    @Autowired
    private ProductClient productClient;
    @Autowired
    private CartMapper cartMapper;


    /**
     * 购物车数据添加方法
     *
     * @param cartSaveParam
     * @return 001成功 002已经存在 003没有库存
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        //查询商品数据

        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product  = productClient.productDetail(productIdParam);
        if (product == null) {
            return  R.fail("商品已经被删除,无法添加到购物车!");
        }
        //检查库存

        if (product.getProductNum() == 0) {

            R ok = R.ok("没有库存数据，无法购买！");
            ok.setCode("003");
            log.info("CartServiceImpl.save业务结束，结果:{}",ok);
            return ok;
        }


//        检查是否添加过
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartSaveParam.getUserId());
        queryWrapper.eq("product_id",cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart != null){
            //更新原来已经有的购物车数据 + 1
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);
            R ok = R.ok("商品已经在购物车,数量+1!");
            ok.setCode("002");
            log.info("CartServiceImpl.save业务结束，结果:{}",ok);
            return ok;
        }

//                添加购物车

        cart = new Cart();
        cart.setNum(1);
        cart.setProductId(cartSaveParam.getProductId());
        cart.setUserId(cartSaveParam.getUserId());
        int rows = cartMapper.insert(cart);
        log.info("CartServiceImpl.save业务结束，结果:{}",rows);
//                结果封装和返回

        CartVo cartVo = new CartVo(product,cart);
        return R.ok("购物车数据添加成功！",cartVo);
    }

    /**
     * 返回购物车数据
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
//查询购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);

//        判断是否存在

        if(carts == null || carts.size() == 0){
            carts = new ArrayList<>();
            return R.ok("购物车为空",carts);

        }

//        获取商品id集合

        List<Integer> productIds = new ArrayList<>();
        for(Cart cart: carts){
            productIds.add(cart.getProductId());
        }
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

//        进行vo封装
//jdk8新特性
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        List<CartVo> cartVoList = new ArrayList<>();

        for(Cart cart: carts){
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()),cart);
            cartVoList.add(cartVo);
        }
        R ok = R.ok("数据库查询成功!",cartVoList);
        log.info("CartServiceImpl.list业务结束,结果为:{}",ok);
        return ok;
    }

    /**
     * 更新购物车数据
     *
     * @param cart
     * @return
     */
    @Override
    public R update(Cart cart) {

        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdParam);

        if (cart.getNum() > product.getProductNum()){
            log.info("CartServiceImpl.update业务结束，结果:{}","库存不足");
            return R.fail("修改失败,库存不足!");
        }

        //数据修改
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());
        Cart newcart = cartMapper.selectOne(queryWrapper);

        newcart.setNum(cart.getNum());

        int rows = cartMapper.updateById(newcart);
        //4.结果封装
        R ok = R.ok("修改购物车数量成功");
        log.info("CartServiceImpl.update业务结束，结果:{}",rows);
        return ok;
    }

    /**
     * 删除购物车数据
     *
     * @param cart
     * @return
     */
    @Override
    public R remove(Cart cart) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());
        int rows = cartMapper.delete(queryWrapper);
        log.info("CartServiceImpl.update业务结束，结果:{}",rows);
        return R.ok("删除购物车数据成功");
    }

    /**
     * 清空对应id的购物车中的商品
     *
     * @param cartIds
     */
    @Override
    public void clearIds(List<Integer> cartIds) {
        cartMapper.deleteBatchIds(cartIds);
        log.info("CartServiceImpl.clearIds业务结束，结果:{}",cartIds);
    }


}
