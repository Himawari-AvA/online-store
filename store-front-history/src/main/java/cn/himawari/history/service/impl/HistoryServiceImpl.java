package cn.himawari.history.service.impl;

import cn.himawari.clients.ProductClient;
import cn.himawari.history.mapper.HistoryMapper;
import cn.himawari.history.service.HistoryService;
import cn.himawari.param.ProductHistoryParam;
import cn.himawari.pojo.History;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private ProductClient productClient;
    /**
     * 收藏（历史记录）添加的方法
     *
     * @param history
     * @return
     */
    @Override
    public R save(History history) {
        log.info("111111111111111");
    QueryWrapper<History> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id",history.getUserId());
    queryWrapper.eq("product_id",history.getProductId());

    Long count = historyMapper.selectCount(queryWrapper);
        log.info("2222222222222");
    if(count>0){
        return R.fail("已经添加，无需重复添加");
    }

    history.setCollectTime(System.currentTimeMillis());

        log.info("3333333333333333333");
    int rows = historyMapper.insert(history);
        log.info("4444444444444444");
    log.info("HistoryServiceImpl.save业务结束，结果:{}",rows);

        return R.ok("添加成功");
    }

    /**
     * 根据用户id查询商品信息集合
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {

        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.select("product_id");
        List<Object> idsObject = historyMapper.selectObjs(queryWrapper);
        ProductHistoryParam productHistoryParam = new ProductHistoryParam();

        List<Integer> ids = new ArrayList<>();
        for (Object o : idsObject) {
            ids.add((Integer) o);
        }
        productHistoryParam.setProductIds(ids);
        R r = productClient.productIds(productHistoryParam);
        log.info("HistoryServiceImpl.list方法结束，结果：{}",r);
        return r;
    }

    /**
     * 删除历史记录
     *
     * @param history
     * @return
     */
    @Override
    public R remove(History history) {
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",history.getUserId());
        queryWrapper.eq("product_id",history.getProductId());
        int rows = historyMapper.delete(queryWrapper);
        log.info("HistoryServiceImpl.remove方法结束，结果：{}",rows);
        return R.ok("历史记录删除成功！");
    }


}
