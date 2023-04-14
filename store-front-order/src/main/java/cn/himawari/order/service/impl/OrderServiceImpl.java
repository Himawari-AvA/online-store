package cn.himawari.order.service.impl;

import cn.himawari.clients.ProductClient;
import cn.himawari.clients.UserClient;
import cn.himawari.order.mapper.OrderMapper;
import cn.himawari.order.service.OrderService;
import cn.himawari.param.AddressRemoveParam;
import cn.himawari.param.OrderParam;
import cn.himawari.param.PageParam;
import cn.himawari.param.ProductCollectParam;
import cn.himawari.pojo.Address;
import cn.himawari.pojo.Order;
import cn.himawari.pojo.Product;
import cn.himawari.to.OrderToProduct;
import cn.himawari.utils.R;
import cn.himawari.vo.AdminOrderVo;
import cn.himawari.vo.CartVo;
import cn.himawari.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 订单数据保存业务
     *
     * @param orderParam
     * @return
     */
    @Transactional
    @Override
    public R save(OrderParam orderParam) {
        /**
         * 1.将购物车数据转成订单数据
         * 2.进行订单没数据的批量插入
         * 3.商品库存修改信息
         * 4.发送购物车库存修改信息
         */
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();

        Integer userId = orderParam.getUserId();
        Integer addressId = orderParam.getAddressId();
        long orderId = System.currentTimeMillis();
        for (CartVo cartVo:orderParam.getProducts()) {
            cartIds.add(cartVo.getId());//要删除的购物车中的商品的id
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProduct.setNum(cartVo.getNum());
            orderToProducts.add(orderToProduct); //添加用户信息

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductPrice(cartVo.getPrice());
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setAddressId(addressId);
            orderList.add(order);
        }
//       订单数据批量保存
        saveBatch(orderList);


        //发送购物车消息
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);

        //发送商品服务消息


        return R.ok("订单保存成功",orderId);
    }

    /**
     * 分组查询购物车订单数据
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {

        //查询用户对应的全部订单数据
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",userId);
        List<Order> orderList = this.list(orderQueryWrapper);

        //数据按订单分组
        Map<Long, List<Order>> orderMap = orderList.stream().collect(Collectors.groupingBy(Order::getOrderId));
        //查询商品数据
        List<Integer> productIds = orderList.stream().map(Order::getProductId).collect(Collectors.toList());
        //结果集封装,返回即可
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        //商品数据
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();
        //遍历订单项集合
        Integer addressId = 0;
        Address address = new Address();
            AddressRemoveParam addressRemoveParam = new AddressRemoveParam();
        for (List<Order> orders : orderMap.values()) {
            log.info("本次addressId:{}",addressId);
            log.info("本次orders:{}",orders);
            log.info("本次orders.get(0):{}",orders.get(0));
            log.info("本次orders.get(0).getAddressId():{}",orders.get(0).getAddressId());

            addressId = orders.get(0).getAddressId();
            addressRemoveParam.setId(addressId);
            address = userClient.getone(addressRemoveParam);
            log.info(address.toString());
            log.info(address.getAddress());
            log.info(address.getLinkman());
            log.info(address.getPhone());
            List<OrderVo> orderVos = new ArrayList<>();
            //封装每一个集合
            for (Order order : orders) {
                //返回vo数据封装
                OrderVo orderVo = new OrderVo();
//                Product product = productMap.get(order.getProductId());
//                orderVo.setProductName(product.getProductName());
//                orderVo.setProductPicture(product.getProductPicture());
//                orderVo.setId(order.getId());
//                orderVo.setOrderId(order.getOrderId());
//                orderVo.setOrderTime(order.getOrderTime());
//                orderVo.setProductNum(order.getProductNum());
//                orderVo.setProductId(order.getProductId());
//                orderVo.setProductPrice(order.getProductPrice());
//                orderVo.setUserId(order.getUserId());
                BeanUtils.copyProperties(order,orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());

                orderVo.setAddress(address.getAddress());
                orderVo.setLinkman(address.getLinkman());
                orderVo.setPhone(address.getPhone());

                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }

        R ok = R.ok("订单数据获取成功",result);
        log.info("OrderServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 检查订单中是否有商品引用
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        Long count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("有"+count+"件订单商品正在引用，删除失败！");

        }
        return R.ok("订单无商品引用");

    }

    /**
     * 后台管理查询订单数据
     *
     * @param pageParam
     * @return
     */
    @Override
    public R adminList(PageParam pageParam) {
        int offset = (pageParam.getCurrentPage()-1) * pageParam.getPageSize();
        int pageSize = pageParam.getPageSize();
         List<AdminOrderVo> adminOrderVoList =  orderMapper.selectAdminOrder(offset,pageSize);
         return R.ok("订单数据查询成功",adminOrderVoList);
    }

    /**
     * 根据订单id取消订单，修改相关的订单状态
     *
     * @param orderId
     * @return
     */
    @Override
    public R cancel(Long orderId) {
        final int[] i = {0};
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        int count = Math.toIntExact(orderMapper.selectCount(queryWrapper));
        orderList.forEach(order -> {
            order.setOrderState(2);
            i[0] += orderMapper.updateById(order);
        });
        if(i[0] == count){
            return R.ok("订单取消成功",count);
        }else{
            return R.fail("订单取消失败");
        }

    }

    /**
     * 订单完成支付后，修改订单状态为1
     *
     * @param orderId
     * @return
     */
    @Override
    public R pay(Long orderId) {
        final int[] i = {0};
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        int count = Math.toIntExact(orderMapper.selectCount(queryWrapper));
        orderList.forEach(order -> {
            order.setOrderState(1);
            i[0] += orderMapper.updateById(order);
        });
        if(i[0] == count){
            return R.ok("订单支付成功",count);
        }else{
            return R.fail("订单支付失败");
        }

    }
}
