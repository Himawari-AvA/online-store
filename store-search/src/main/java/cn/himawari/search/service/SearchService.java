package cn.himawari.search.service;

import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Product;
import cn.himawari.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface SearchService {

    /**
     * 根据关键字和分页查询数据
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 商品同步：插入与更新
     * @param product
     * @return
     */
    R save(Product product) throws IOException;

    /**
     * 进行es库的商品删除
     * @param productId
     * @return
     */
    R remove(Integer productId) throws IOException;
}
