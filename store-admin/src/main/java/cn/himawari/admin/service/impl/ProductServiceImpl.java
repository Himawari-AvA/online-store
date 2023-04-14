package cn.himawari.admin.service.impl;

import cn.himawari.admin.service.ProductService;
import cn.himawari.clients.ProductClient;
import cn.himawari.clients.SearchClient;
import cn.himawari.param.ProductSaveParam;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Product;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private ProductClient productClient;


    /**
     * 搜索查询和全部商品查询
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        R search = searchClient.search(productSearchParam);
        log.info("ProductServiceImpl.search业务结束，结果：{}",search);
        return search;
    }

    /**
     * 商品数据保存
     *
     * @param productSaveParam
     * @return
     */
    @Override
    public R save(ProductSaveParam productSaveParam) {
        log.info("admin-service-ProductServiceImpl.save业务开始，参数：{}",productSaveParam);
        R r = productClient.adminSave(productSaveParam);
        log.info("ProductServiceImpl.save业务结束，结果：{}",r);
        return r;
    }

    /**
     * 商品数据更新
     *
     * @param product
     * @return
     */
    @Override
    public R update(Product product) {
        R r = productClient.adminUpdate(product);
        log.info("ProductServiceImpl.update业务结束，结果：{}",r);
        return r;
    }

    /**
     * 商品删除
     *
     * @param productId
     * @return
     */
    @Override
    public R remove(Integer productId) {
        R r = productClient.adminRemove(productId);
        log.info("ProductServiceImpl.remove业务结束，结果：{}",r);
        return r;
    }
}
