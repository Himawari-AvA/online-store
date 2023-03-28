package cn.himawari.admin.service;

import cn.himawari.param.PageParam;
import cn.himawari.utils.R;

public interface OrderService {
    /**
     * 查询订单数据
     * @param pageParam
     * @return
     */
    R list(PageParam pageParam);
}
