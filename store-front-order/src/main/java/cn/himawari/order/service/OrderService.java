package cn.himawari.order.service;

import cn.himawari.param.OrderParam;
import cn.himawari.param.PageParam;
import cn.himawari.pojo.Order;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {


    /**
     * 订单数据保存，订单状态为默认0
     * @param orderParam
     * @return
     */
    R save(OrderParam orderParam);

    /**
     * 分组查询购物车订单数据
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 检查订单中是否有商品引用
     * @param productId
     * @return
     */
    R check(Integer productId);

    /**
     * 后台管理查询订单数据
     * @param pageParam
     * @return
     */
    R adminList(PageParam pageParam);

    /**
     * 根据订单id取消订单，修改相关的订单状态为2
     * @param orderId
     * @return
     */
    R cancel(Long orderId);

    /**
     * 订单完成支付后，修改订单状态为1
     * @param orderId
     * @return
     */
    R pay(Long orderId);
}
