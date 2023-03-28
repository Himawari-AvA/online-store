package cn.himawari.history.service;

import cn.himawari.pojo.History;
import cn.himawari.utils.R;

public interface HistoryService {

    /**
     * 收藏（历史记录）添加的方法
     * @param history
     * @return
     */
    R save(History history);

    /**
     * 根据用户id查询商品信息集合
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 删除历史记录
     * @param history
     * @return
     */
    R remove(History history);

    /**
     * 根据id删除历史记录商品
     * @param productId
     * @return
     */
    R removeByProductId(Integer productId);
}
