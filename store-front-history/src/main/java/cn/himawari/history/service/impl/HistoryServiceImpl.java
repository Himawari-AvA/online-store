package cn.himawari.history.service.impl;

import cn.himawari.clients.ProductClient;
import cn.himawari.history.mapper.HistoryMapper;
import cn.himawari.history.service.HistoryService;
import cn.himawari.param.ProductHistoryParam;
import cn.himawari.param.ProductIdParam;
import cn.himawari.pojo.History;
import cn.himawari.pojo.Order;
import cn.himawari.pojo.Product;
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
//        return R.fail("已经添加，无需重复添加");
        historyMapper.delete(queryWrapper);
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
        List<Product> productList = new ArrayList<>();

        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id",userId);
//        queryWrapper.select("product_id");
////        queryWrapper.orderByDesc("collect_time");
//        List<Object> idsObject = historyMapper.selectObjs(queryWrapper);
//        ProductHistoryParam productHistoryParam = new ProductHistoryParam();

        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("collect_time");
        List<History> historyList = historyMapper.selectList(queryWrapper);
        ProductHistoryParam productHistoryParam = new ProductHistoryParam();


        List<Integer> ids = new ArrayList<>();
//        for (Object o : idsObject) {
//            ids.add((Integer) o);
//        }
        for (History history : historyList) {
//            log.info("查看第一次id集合顺序:{}",history.getProductId());
            ids.add(history.getProductId());
        }
//        log.info("查看第二次id集合顺序:{}",ids);
        productHistoryParam.setProductIds(ids);
//        log.info("查看第三次id集合顺序:{}",productHistoryParam.getProductIds());
//        R r = productClient.productIds(productHistoryParam);
//        log.info("查看第四次id集合顺序:{}",r.getData());

        ids.forEach(id->{
            ProductIdParam productIdParam = new ProductIdParam();
            productIdParam.setProductID(id);
            productList.add(productClient.productDetail(productIdParam));
        });
        log.info("HistoryServiceImpl.list方法结束，结果：{}",productList);
        return R.ok(productList);
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

    /**
     * 根据id删除历史记录商品
     *
     * @param productId
     * @return
     */
    @Override
    public R removeByProductId(Integer productId) {
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        int rows = historyMapper.delete(queryWrapper);
        log.info("HistoryServiceImpl.removeByProductId，结果：{}",rows);
        return R.ok("历史记录商品删除成功");

    }


}
