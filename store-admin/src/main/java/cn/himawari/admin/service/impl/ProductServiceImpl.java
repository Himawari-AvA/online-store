package cn.himawari.admin.service.impl;

import cn.himawari.admin.service.ProductService;
import cn.himawari.clients.SearchClient;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SearchClient searchClient;

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
}
