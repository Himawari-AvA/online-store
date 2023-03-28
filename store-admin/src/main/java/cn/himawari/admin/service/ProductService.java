package cn.himawari.admin.service;

import cn.himawari.param.ProductSaveParam;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Product;
import cn.himawari.utils.R;

public interface ProductService {
    /**
     * 搜索查询和全部商品查询
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 商品数据保存
     * @param productSaveParam
     * @return
     */
    R save(ProductSaveParam productSaveParam);

    /**
     * 商品数据更新
     * @param product
     * @return
     */
    R update(Product product);

    /**
     * 商品删除
     * @param productId
     * @return
     */
    R remove(Integer productId);
}
