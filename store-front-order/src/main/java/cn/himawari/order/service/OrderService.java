package cn.himawari.order.service;

import cn.himawari.param.OrderParam;
import cn.himawari.pojo.Order;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {


    /**
     * 订单数据保存业务
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
}
